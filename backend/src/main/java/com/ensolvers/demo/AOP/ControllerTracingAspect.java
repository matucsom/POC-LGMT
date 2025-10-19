package com.ensolvers.demo.AOP;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
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

@Slf4j
@Aspect
@Component
public class ControllerTracingAspect {

    private final ObjectMapper mapper = new ObjectMapper();

    @Around("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public Object enrichControllerTrace(ProceedingJoinPoint joinPoint) throws Throwable {
        Span span = Span.current(); // ✅ reutiliza el span actual

        if (!span.getSpanContext().isValid()) {
            // fallback si no hay span activo (por ejemplo, fuera del request)
            return joinPoint.proceed();
        }

        try (Scope scope = span.makeCurrent()) {
            // Capturamos info contextual
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attrs != null ? attrs.getRequest() : null;

            if (request != null) {
                span.setAttribute("http.query", request.getQueryString());
                span.setAttribute("http.user_agent", request.getHeader("User-Agent"));
            }

            span.setAttribute("user.id", TracingUtils.getCurrentUserId());
            span.setAttribute("user.roles", TracingUtils.getCurrentUserRoles());

            // Captura de argumentos
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0) {
                span.setAttribute("params.count", args.length);

                for (Object arg : args) {
                    if (arg == null) continue;
                    try {
                        String json = mapper.writeValueAsString(arg);
                        // ⚠️ limitamos el tamaño para evitar saturar Tempo
                        if (json.length() > 5000)
                            json = json.substring(0, 5000) + "...[truncated]";
                        span.setAttribute("param." + arg.getClass().getSimpleName(), json);
                    } catch (Exception e) {
                        span.setAttribute("param." + arg.getClass().getSimpleName(), "serialization_error");
                    }
                }
            }

            Object result = joinPoint.proceed();

            // Captura del resultado
            try {
                String jsonResult = mapper.writeValueAsString(result);
                if (jsonResult.length() > 5000)
                    jsonResult = jsonResult.substring(0, 5000) + "...[truncated]";
                span.setAttribute("response", jsonResult);
            } catch (Exception e) {
                span.setAttribute("response", "serialization_error");
            }

            return result;

        } catch (Exception e) {
            span.recordException(e);
            span.setStatus(StatusCode.ERROR);
            throw e;
        }
    }
}

