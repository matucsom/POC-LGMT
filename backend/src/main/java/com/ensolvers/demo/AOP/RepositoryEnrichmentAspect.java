package com.ensolvers.demo.AOP;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RepositoryEnrichmentAspect {

    private final ObjectMapper mapper = new ObjectMapper();

    @Around("@within(org.springframework.stereotype.Repository)")
    public Object enrichRepositorySpan(ProceedingJoinPoint joinPoint) throws Throwable {
        Span currentSpan = Span.current();

        if (currentSpan != null && currentSpan.getSpanContext().isValid()) {
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0) {
                for (Object arg : args) {
                    if (arg == null) continue;
                    try {
                        String json = mapper.writeValueAsString(arg);
                        currentSpan.setAttribute("repo.param." + arg.getClass().getSimpleName(), json);
                    } catch (Exception e) {
                        currentSpan.setAttribute("repo.param." + arg.getClass().getSimpleName(), "serialization_error");
                    }
                }
            }
        }

        try (Scope scope = Context.current().makeCurrent()) {
            Object result = joinPoint.proceed();

            if (currentSpan != null && currentSpan.getSpanContext().isValid()) {
                try {
                    String json = mapper.writeValueAsString(result);
                    currentSpan.setAttribute("repo.result", json);
                } catch (Exception e) {
                    currentSpan.setAttribute("repo.result", "serialization_error");
                }
            }

            return result;
        }
    }
}
