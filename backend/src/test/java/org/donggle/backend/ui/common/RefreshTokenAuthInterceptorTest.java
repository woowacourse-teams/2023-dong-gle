package org.donggle.backend.ui.common;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.donggle.backend.application.repository.TokenRepository;
import org.donggle.backend.domain.auth.JwtTokenProvider;
import org.donggle.backend.domain.auth.RefreshToken;
import org.donggle.backend.exception.authentication.ExpiredRefreshTokenException;
import org.donggle.backend.exception.authentication.InvalidRefreshTokenException;
import org.donggle.backend.exception.authentication.NoRefreshTokenInCookieException;
import org.donggle.backend.exception.notfound.RefreshTokenNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class RefreshTokenAuthInterceptorTest {

    private JwtTokenProvider jwtTokenProvider;
    private TokenRepository tokenRepository;
    private RefreshTokenAuthInterceptor refreshTokenAuthInterceptor;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = mock(JwtTokenProvider.class);
        tokenRepository = mock(TokenRepository.class);
        refreshTokenAuthInterceptor = new RefreshTokenAuthInterceptor(jwtTokenProvider, tokenRepository);
    }


    @Test
    @DisplayName("유효한 Refresh 토큰이 있는 경우 preHandle 메서드는 true를 반환한다")
    void shouldReturnTrueWhenRefreshTokenIsValid() {
        //given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RefreshToken mockToken = mock(RefreshToken.class);

        given(request.getCookies()).willReturn(new Cookie[]{new Cookie("refreshToken", "someToken")});
        given(jwtTokenProvider.getPayload("someToken")).willReturn(1L);
        given(tokenRepository.findByMemberId(1L)).willReturn(java.util.Optional.of(mockToken));
        given(mockToken.isDifferentFrom("someToken")).willReturn(false);
        given(jwtTokenProvider.inValidTokenUsage("someToken")).willReturn(false);

        //when
        final boolean result = refreshTokenAuthInterceptor.preHandle(request, response, new Object());

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("유효한 Refresh 토큰이 유효하지 않을때 에러")
    void differentRefreshTokenException() {
        //given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RefreshToken refreshToken = mock(RefreshToken.class);

        given(request.getCookies()).willReturn(new Cookie[]{new Cookie("refreshToken", "someToken")});
        given(jwtTokenProvider.getPayload("someToken")).willReturn(1L);
        given(tokenRepository.findByMemberId(1L)).willReturn(Optional.of(refreshToken));
        given(refreshToken.isDifferentFrom("someToken")).willReturn(true);

        //when
        //then
        assertThatThrownBy(
                () -> refreshTokenAuthInterceptor.preHandle(request, response, new Object())
        ).isInstanceOf(InvalidRefreshTokenException.class);
    }

    @Test
    @DisplayName("Refresh 토큰을 찾을 수 없을 때 에러")
    void RefreshTokenNotFoundException() {
        //given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        given(request.getCookies()).willReturn(new Cookie[]{new Cookie("refreshToken", "someToken")});
        given(jwtTokenProvider.getPayload("someToken")).willReturn(1L);
        given(tokenRepository.findByMemberId(1L)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(
                () -> refreshTokenAuthInterceptor.preHandle(request, response, new Object())
        ).isInstanceOf(RefreshTokenNotFoundException.class);
    }

    @Test
    @DisplayName("유효기간이 지난 Refresh 토큰 에러")
    void invalidRefreshTokenException() {
        //given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RefreshToken refreshToken = mock(RefreshToken.class);

        given(request.getCookies()).willReturn(new Cookie[]{new Cookie("refreshToken", "someToken")});
        given(jwtTokenProvider.getPayload("someToken")).willReturn(1L);
        given(tokenRepository.findByMemberId(1L)).willReturn(Optional.of(refreshToken));
        given(refreshToken.isDifferentFrom("someToken")).willReturn(false);
        given(jwtTokenProvider.inValidTokenUsage("someToken")).willReturn(true);
        //when
        //then
        assertThatThrownBy(
                () -> refreshTokenAuthInterceptor.preHandle(request, response, new Object())
        ).isInstanceOf(ExpiredRefreshTokenException.class);
    }

    @Test
    @DisplayName("쿠키에 refreshToken이 없으면 NoRefreshTokenInCookieException 에러")
    void shouldThrowNoRefreshTokenInCookieExceptionWhenNoRefreshTokenInCookies() {
        //given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        given(request.getCookies()).willReturn(new Cookie[]{});

        //when
        //then
        assertThatThrownBy(
                () -> refreshTokenAuthInterceptor.preHandle(request, response, new Object())
        ).isInstanceOf(NoRefreshTokenInCookieException.class);
    }
}
