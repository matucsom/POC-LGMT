package com.ensolvers.demo.config.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);
    private final String serviceName;

    public RequestResponseLoggingFilter(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId);

        long startTime = System.currentTimeMillis();

        try {
            logger.info("REQUEST: {} {} - user={} - time={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getRemoteUser(),
                    Instant.now());

            filterChain.doFilter(request, response);

            long duration = System.currentTimeMillis() - startTime;
            logger.info("RESPONSE: {} {} - status={} - duration={}ms - traceId={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    duration,
                    traceId);

        } catch (Exception ex) {
            logger.error("ERROR: {} {} - traceId={} - message={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    traceId,
                    ex.getMessage(), ex);
            throw ex;
        } finally {
            MDC.clear();
        }
    }
}
