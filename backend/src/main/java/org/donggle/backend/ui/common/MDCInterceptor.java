package org.donggle.backend.ui.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class MDCInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {
        final String requestId = UUID.randomUUID().toString();
        final String requestUrl = request.getRequestURI();
        final String clientIP = getClientIP(request);
        
        MDC.put("requestId", requestId);
        MDC.put("requestUrl", requestUrl);
        MDC.put("clientIP", clientIP);
        
        return true;
    }
    
    private String getClientIP(final HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.hasLength(ip)) {
            ip = ip.split(",")[0].strip();
        } else {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    @Override
    public void afterCompletion(final HttpServletRequest request,
                                final HttpServletResponse response,
                                final Object handler,
                                final Exception ex) {
        MDC.clear();
    }
}
