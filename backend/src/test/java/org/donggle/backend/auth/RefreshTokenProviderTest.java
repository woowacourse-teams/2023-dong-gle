package org.donggle.backend.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RefreshTokenProviderTest {

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
}