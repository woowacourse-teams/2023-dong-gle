package org.donggle.backend.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.AuthService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class RefreshTokenAuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        final String token = AuthorizationExtractor.extract(request);
        if (jwtTokenProvider.inValidTokenUsage(token)) {
            throw new AuthExpiredException("토큰 기간이 만료되었습니다.");
        }

        if (authService.inValidateTokenOwner(jwtTokenProvider.getPayload(token), token)) {
            throw new AuthExpiredException("토큰 기간이 만료되었습니다.");

        }
        return true;
    }
}
