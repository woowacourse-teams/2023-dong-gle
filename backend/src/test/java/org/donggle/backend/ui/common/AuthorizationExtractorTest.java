package org.donggle.backend.ui.common;

import jakarta.servlet.http.HttpServletRequest;
import org.donggle.backend.exception.authentication.InvalidAuthorizationHeaderTypeException;
import org.donggle.backend.exception.notfound.AuthorizationHeaderNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AuthorizationExtractorTest {

    @Test
    @DisplayName("Authorization 해더값 추출")
    void shouldReturnTokenWhenHeaderIsCorrect() {
        //given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        given(request.getHeader(HttpHeaders.AUTHORIZATION)).willReturn("Bearer someToken");

        //when
        final String token = AuthorizationExtractor.extract(request);

        //then
        assertThat("someToken").isEqualTo(token);
    }

    @Test
    @DisplayName("Authorization 해더값이 null일때 에러")
    void shouldThrowExceptionWhenHeaderIsMissing() {
        //given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        given(request.getHeader(HttpHeaders.AUTHORIZATION)).willReturn(null);

        //when
        //then
        assertThatThrownBy(
                () -> AuthorizationExtractor.extract(request)
        ).isInstanceOf(AuthorizationHeaderNotFoundException.class);
    }

    @Test
    @DisplayName("Authorization 해더값이 Bearer이 아닐때 에러")
    void shouldThrowExceptionWhenHeaderIsNotBearer() {
        //given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        given(request.getHeader(HttpHeaders.AUTHORIZATION)).willReturn("Basic someToken");

        //when
        //then
        assertThatThrownBy(
                () -> AuthorizationExtractor.extract(request)
        ).isInstanceOf(InvalidAuthorizationHeaderTypeException.class);
    }
}