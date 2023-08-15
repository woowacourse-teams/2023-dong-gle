package org.donggle.backend.auth.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.TokenRepository;
import org.donggle.backend.auth.JwtTokenProvider;
import org.donggle.backend.auth.RefreshToken;
import org.donggle.backend.auth.exception.NoSuchTokenException;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@RequiredArgsConstructor
public class RefreshTokenAuthInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {
        final String comparisonRefreshToken = extract(request);
        final Long memberId = jwtTokenProvider.getPayload(comparisonRefreshToken);
        final RefreshToken originalRefreshToken = tokenRepository.findByMemberId(memberId)
                .orElseThrow(NoSuchTokenException::new);

        if (originalRefreshToken.isDifferentFrom(comparisonRefreshToken) || jwtTokenProvider.inValidTokenUsage(comparisonRefreshToken)) {
            throw new NoSuchTokenException();
        }

        return true;
    }

    private String extract(final HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .orElseThrow(NoSuchTokenException::new)
                .getValue();
    }
}
