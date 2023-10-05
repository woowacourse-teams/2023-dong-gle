package org.donggle.backend.ui.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.donggle.backend.domain.auth.JwtTokenProvider;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }
        final String token = AuthorizationExtractor.extract(request);
        jwtTokenProvider.getPayload(token);
        return true;
    }
}
