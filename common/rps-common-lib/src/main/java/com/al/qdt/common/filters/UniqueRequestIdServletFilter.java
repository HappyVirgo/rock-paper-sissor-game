package com.al.qdt.common.filters;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

/**
 * The filter fills MDC context with request Id - the value of {@code requestId} URI parameter.
 */
@Slf4j
@Order(1)
@Component
@ConditionalOnWebApplication(type = SERVLET)
public class UniqueRequestIdServletFilter extends OncePerRequestFilter {
    private static final String X_REQUEST_ID = "X-Request-ID";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            addXRequestId(request);
            log.info("Path: {}, method: {}, query {}",
                    request.getRequestURI(), request.getMethod(), request.getQueryString());
            response.setHeader(X_REQUEST_ID, MDC.get(X_REQUEST_ID));
            filterChain.doFilter(request, response);
        } finally {
            log.info("StatusCode {}, path: {}, method: {}, query {}",
                    response.getStatus(), request.getRequestURI(), request.getMethod(), request.getQueryString());
            MDC.clear();
        }
    }

    /**
     * Register request correlationId from servlet request.
     *
     * @param request servlet request
     */
    private void addXRequestId(HttpServletRequest request) {
        final var xRequestId = request.getHeader(X_REQUEST_ID);
        if (StringUtils.isEmpty(xRequestId)) {
            MDC.put(X_REQUEST_ID, UUID.randomUUID().toString());
        } else {
            MDC.put(X_REQUEST_ID, xRequestId);
        }
    }

    @Override
    protected boolean isAsyncDispatch(HttpServletRequest request) {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}
