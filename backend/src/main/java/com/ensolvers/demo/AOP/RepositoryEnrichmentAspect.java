package com.ensolvers.demo.AOP;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Por ahora vamos a dejar que el Otel Agent Java lo orqueste solo ,
 * realmente presento muchos problemas para intentar enriquecerlos y pasaba que se duplicaban spans o se agarran spans que no eran ,
 * o la info no se agregaba, asi que optamos por dejarlo que lo maneje de manera automatica el javaagentOtel
 * */
@Slf4j
@Aspect
@Component
public class RepositoryEnrichmentAspect {

   /* private final ObjectMapper mapper = new ObjectMapper();
    private final DataSource dataSource;

    public RepositoryEnrichmentAspect(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Around("execution(* org.springframework.data.repository.Repository+.*(..))")
    public Object enrichRepositoryTrace(ProceedingJoinPoint joinPoint) throws Throwable {

        // 🔹 Obtenemos el span actual (creado por el autoinstrumentador de OTel)
        Span currentSpan = Span.current();

        boolean hasActiveSpan = currentSpan != null && currentSpan.getSpanContext().isValid();

        if (hasActiveSpan) {
            currentSpan.setAttribute("layer", "repository");
            currentSpan.setAttribute("repository.class", joinPoint.getTarget().getClass().getSimpleName());
            currentSpan.setAttribute("repository.method", joinPoint.getSignature().getName());

            // Argumentos del método (resumen)
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0) {
                Map<String, String> argsMap = new LinkedHashMap<>();
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    String value;
                    if (arg == null) {
                        value = "null";
                    } else if (isPrimitiveOrWrapper(arg.getClass()) || arg instanceof String) {
                        value = arg.toString();
                    } else {
                        value = arg.getClass().getSimpleName();
                    }
                    argsMap.put("arg" + i, value);
                }
                currentSpan.setAttribute("repository.params", argsMap.toString());
            }

            // Información opcional de conexión
            try (Connection conn = dataSource.getConnection()) {
                currentSpan.setAttribute("repository.db.url", conn.getMetaData().getURL());
                currentSpan.setAttribute("repository.db.user", conn.getMetaData().getUserName());
            } catch (SQLException e) {
                log.debug("No se pudo obtener metadata de la conexión", e);
            }
        }

        // Continuamos normalmente
        Object result = joinPoint.proceed();

        if (hasActiveSpan) {
            if (result != null) {
                currentSpan.setAttribute("repository.result.type", result.getClass().getSimpleName());
                if (result instanceof Collection<?>) {
                    currentSpan.setAttribute("repository.result.size", ((Collection<?>) result).size());
                }
            } else {
                currentSpan.setAttribute("repository.result", "null");
            }
        }

        return result;
    }

    private boolean isPrimitiveOrWrapper(Class<?> type) {
        return type.isPrimitive()
                || type == Integer.class
                || type == Long.class
                || type == Double.class
                || type == Float.class
                || type == Short.class
                || type == Byte.class
                || type == Boolean.class
                || type == Character.class;
    }*/
}
