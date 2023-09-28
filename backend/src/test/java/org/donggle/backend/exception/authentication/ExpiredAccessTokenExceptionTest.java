package org.donggle.backend.exception.authentication;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExpiredAccessTokenExceptionTest {

    @Test
    @DisplayName("ExpiredAccessTokenException의 힌트 테스트")
    void getHint() {
        assertThat(new ExpiredAccessTokenException().getHint()).isEqualTo("AccessToken이 만료되었습니다. RefreshToken값을 요청하세요.");
    }

    @Test
    @DisplayName("ExpiredAccessTokenException의 상태코드 테스트")
    void getErrorCode() {
        assertThat(new ExpiredAccessTokenException().getErrorCode()).isEqualTo(4011);
    }
}
