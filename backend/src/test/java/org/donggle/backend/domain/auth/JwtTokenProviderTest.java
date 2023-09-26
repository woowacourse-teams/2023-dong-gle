package org.donggle.backend.domain.auth;

import org.donggle.backend.exception.authentication.ExpiredAccessTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtTokenProviderTest {
    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
            "wjdgustmdwjdgustmdwjdgustmdwjsadasdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmd",
            600000,
            1200000
    );

    @Test
    @DisplayName("RefreshToken 발급 테스트")
    void createRefreshToken() {
        //given
        //when
        final String token = jwtTokenProvider.createRefreshToken(1234L);
        final Long payload = jwtTokenProvider.getPayload(token);

        //then
        assertThat(payload).isEqualTo(1234L);
    }

    @Test
    @DisplayName("AccessToken 발급 테스트")
    void createAccessToken() {
        //given
        //when
        final String token = jwtTokenProvider.createAccessToken(23L);
        final Long payload = jwtTokenProvider.getPayload(token);

        //then
        assertThat(payload).isEqualTo(23L);
    }

    @Test
    @DisplayName("유효한 토큰 사용 시 inValidTokenUsage 메서드는 false를 반환해야 한다")
    void validTokenUsageTest() {
        //given
        final String token = jwtTokenProvider.createAccessToken(1L);

        //when
        final boolean result = jwtTokenProvider.inValidTokenUsage(token);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("만료된 토큰 사용 시 예외 발생")
    void expiredTokenUsageTest() {
        //given
        final JwtTokenProvider jwt = new JwtTokenProvider(
                "wjdgustmdwjdgustmdwjdgustmdwjsadasdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmd",
                1,
                1200000
        );
        final String token = jwt.createAccessToken(1L);
        // 유효시간을 초과하기 위해 대기

        //when
        //then
        assertThatThrownBy(
                () -> jwtTokenProvider.inValidTokenUsage(token)
        ).isInstanceOf(ExpiredAccessTokenException.class);
    }

    @Test
    @DisplayName("잘못된 토큰 사용 시 inValidTokenUsage 메서드는 true를 반환해야 한다")
    void invalidTokenUsageTest() {
        //given
        final String invalidToken = "InvalidToken";

        //when
        final boolean result = jwtTokenProvider.inValidTokenUsage(invalidToken);

        //then
        assertThat(result).isTrue();
    }
}
