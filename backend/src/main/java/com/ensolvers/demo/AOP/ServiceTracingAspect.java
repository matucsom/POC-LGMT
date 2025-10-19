package com.ensolvers.demo.AOP;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.StatusCode;
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
    private final ObjectMapper mapper = new ObjectMapper(); // reutilizable

    @Around("@within(org.springframework.stereotype.Service)")
    public Object traceService(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().toShortString();

        Span span = tracer.spanBuilder(method)
                .setAttribute("layer", "service")
                .setAttribute("service.method", method)
                .setAttribute("user.id", TracingUtils.getCurrentUserId())
                .startSpan();

        try (Scope scope = span.makeCurrent()) {
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0) {
                span.setAttribute("params.count", args.length);

                for (Object arg : args) {
                    try {
                        // Serializamos el objeto completo, sin filtrar nada
                        String json = mapper.writeValueAsString(arg);
                        span.setAttribute("param." + arg.getClass().getSimpleName(), json);
                    } catch (Exception e) {
                        span.setAttribute("param." + arg.getClass().getSimpleName(), "serialization_error");
                    }
                }
            }

            Object result = joinPoint.proceed();
            span.setStatus(StatusCode.OK);
            return result;

        } catch (Exception e) {
            span.recordException(e);
            span.setStatus(StatusCode.ERROR);
            throw e;

        } finally {
            span.end();
        }
    }
}

