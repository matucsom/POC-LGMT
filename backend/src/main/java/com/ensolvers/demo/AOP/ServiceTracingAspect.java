package com.ensolvers.demo.AOP;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.*;
import io.opentelemetry.context.Scope;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class ServiceTracingAspect {

    private final Tracer tracer = GlobalOpenTelemetry.getTracer("rh-system.service");
    private final ObjectMapper mapper = new ObjectMapper();

    @Around("@within(org.springframework.stereotype.Service)")
    public Object traceService(ProceedingJoinPoint joinPoint) throws Throwable {


        Span span = createSpan(joinPoint);

        try (Scope scope = span.makeCurrent()) {
            enrichWithParameters(span, joinPoint.getArgs());
            Object result = executeAndTrace(joinPoint, span);
            span.setStatus(StatusCode.OK);
            return result;

        } catch (Exception e) {
            handleException(span, e);
            throw e;

        } finally {
            endSpan(span);
        }
    }


    private Span createSpan(ProceedingJoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String spanName = className + "." + methodName;

        Span span = tracer.spanBuilder(spanName)
                .setAttribute("layer", "service")
                .setAttribute("service.class", className)
                .setAttribute("service.method", methodName)
                .setAttribute("user.id", getCurrentUserIdSafe())
                .startSpan();

        log.debug("🟢 Iniciando span de servicio: {}", spanName);
        return span;
    }


    private void enrichWithParameters(Span span, Object[] args) {
        if (args == null || args.length == 0) return;

        span.setAttribute("params.count", args.length);

        for (Object arg : args) {
            if (arg == null) continue;
            String key = "param." + arg.getClass().getSimpleName();

            // Evitar binarios o streams
            if (arg instanceof byte[] || arg instanceof java.io.InputStream) {
                span.setAttribute(key, "binary_skipped");
                continue;
            }

            try {
                String json = mapper.writeValueAsString(arg);
                if (json.length() > 500) json = json.substring(0, 500) + "...(truncated)";
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
        log.debug("✅ Método {} ejecutado en {} ms", joinPoint.getSignature().toShortString(), duration);

        return result;
    }


    private void handleException(Span span, Exception e) {
        span.recordException(e);
        span.setStatus(StatusCode.ERROR);
        log.error("🔴 Error capturado en span: {}", e.getMessage(), e);
    }


    private void endSpan(Span span) {
        span.end();
        log.debug("🔚 Span finalizado: {}", span.getSpanContext().getTraceId());
    }


    private String getCurrentUserIdSafe() {
        try {
            String userId = TracingUtils.getCurrentUserId();
            return (userId != null) ? userId : "anonymous";
        } catch (Exception e) {
            return "unknown";
        }
    }
}
