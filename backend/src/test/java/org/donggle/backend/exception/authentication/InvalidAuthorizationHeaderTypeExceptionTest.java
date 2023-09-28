package org.donggle.backend.exception.authentication;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InvalidAuthorizationHeaderTypeExceptionTest {

    @Test
    @DisplayName("ExpiredRefreshTokenException의 힌트 테스트")
    void getHint() {
        Assertions.assertThat(new ExpiredRefreshTokenException().getHint()).isEqualTo("RefreshToken이 만료되었습니다. 다시 로그인을 진행하세요.");
    }

}