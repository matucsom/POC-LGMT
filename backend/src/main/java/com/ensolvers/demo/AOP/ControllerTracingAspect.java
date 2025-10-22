package com.ensolvers.demo.AOP;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.context.Scope;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 🔍 Aspecto que enriquece automaticamente los spans actuales en los controladores.
 *
 * Flujo principal:
 * 1 Reutiliza el span activo.
 * 2 Agrega información HTTP y del usuario.
 * 3 Serializa argumentos y respuesta.
 * 4 Maneja excepciones y actualiza el span.
 */
@Slf4j
@Aspect
@Component
public class ControllerTracingAspect {

    private final ObjectMapper mapper = new ObjectMapper();


    @Around("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public Object enrichControllerTrace(ProceedingJoinPoint joinPoint) throws Throwable {

        Span span = getCurrentSpan();
        if (!span.getSpanContext().isValid()) {
            return joinPoint.proceed(); // por si no hay span activo
        }

        try (Scope scope = span.makeCurrent()) {

            enrichWithHttpRequest(span);
            enrichWithUser(span);

            serializeArguments(span, joinPoint.getArgs());
            Object result = executeAndTrace(joinPoint, span);
            serializeResult(span, result);

            return result;

        } catch (Exception e) {
            handleException(span, e);
            throw e;
        }
    }


    private Span getCurrentSpan() {
        return Span.current(); // reutiliza el span que proviene de la capa superior (ej: servicio)
    }


    private void enrichWithHttpRequest(Span span) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs != null ? attrs.getRequest() : null;

        if (request != null) {
            span.setAttribute("http.query", request.getQueryString());
            span.setAttribute("http.user_agent", request.getHeader("User-Agent"));
            span.setAttribute("http.path", request.getRequestURI());
            span.setAttribute("http.method", request.getMethod());
        }
    }


    private void enrichWithUser(Span span) {
        span.setAttribute("user.id", safeGetUserId());
        span.setAttribute("user.roles", safeGetUserRoles());
    }

    private String safeGetUserId() {
        try {
            String userId = TracingUtils.getCurrentUserId();
            return (userId != null) ? userId : "anonymous";
        } catch (Exception e) {
            return "unknown";
        }
    }

    private String safeGetUserRoles() {
        try {
            String roles = TracingUtils.getCurrentUserRoles();
            return (roles != null) ? roles : "none";
        } catch (Exception e) {
            return "unknown";
        }
    }


    private void serializeArguments(Span span, Object[] args) {
        if (args == null || args.length == 0) return;

        span.setAttribute("params.count", args.length);
        for (Object arg : args) {
            if (arg == null) continue;
            String key = "param." + arg.getClass().getSimpleName();

            if (arg instanceof byte[] || arg instanceof java.io.InputStream) {
                span.setAttribute(key, "binary_skipped");
                continue;
            }

            try {
                String json = mapper.writeValueAsString(arg);
                if (json.length() > 5000) json = json.substring(0, 5000) + "...[truncated]";
                span.setAttribute(key, json);
            } catch (Exception e) {
                span.setAttribute(key, "serialization_error");
            }
        }
    }


    private Object executeAndTrace(ProceedingJoinPoint joinPoint, Span span) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;

        span.setAttribute("execution.time.ms", duration);
        log.debug("✅ Controller {} ejecutado en {} ms", joinPoint.getSignature().toShortString(), duration);

        return result;
    }


    private void serializeResult(Span span, Object result) {
        if (result == null) {
            span.setAttribute("response", "null");
            return;
        }

        try {
            String jsonResult = mapper.writeValueAsString(result);
            if (jsonResult.length() > 5000) jsonResult = jsonResult.substring(0, 5000) + "...[truncated]";
            span.setAttribute("response", jsonResult);
        } catch (Exception e) {
            span.setAttribute("response", "serialization_error");
        }
    }


    private void handleException(Span span, Exception e) {
        span.recordException(e);
        span.setStatus(StatusCode.ERROR);
        log.error("🔴 Error capturado en span de controller: {}", e.getMessage(), e);
    }
}
