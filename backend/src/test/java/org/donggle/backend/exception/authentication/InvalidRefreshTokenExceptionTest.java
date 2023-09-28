package org.donggle.backend.exception.authentication;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InvalidRefreshTokenExceptionTest {
    @Test
    @DisplayName("InvalidRefreshTokenException의 힌트 테스트")
    void getHint() {
        Assertions.assertThat(new InvalidRefreshTokenException().getHint()).isEqualTo("유효하지 않은 RefreshToken입니다. 다시 로그인을 진행하세요.");
    }
}