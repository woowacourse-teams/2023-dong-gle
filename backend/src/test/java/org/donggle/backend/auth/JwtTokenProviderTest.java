package org.donggle.backend.auth;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class JwtTokenProviderTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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