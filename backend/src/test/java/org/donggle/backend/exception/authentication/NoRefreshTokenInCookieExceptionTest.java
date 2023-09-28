package org.donggle.backend.exception.authentication;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NoRefreshTokenInCookieExceptionTest {

    @Test
    @DisplayName("NoRefreshTokenInCookieException의 힌트 테스트")
    void getHint() {
        assertThat(new NoRefreshTokenInCookieException().getHint()).isEqualTo("쿠키에 RefreshToken이 존재하지 않습니다.");
    }
}