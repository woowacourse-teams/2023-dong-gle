package org.donggle.backend.exception.authentication;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExpiredRefreshTokenExceptionTest {

    @Test
    @DisplayName("InvalidAuthorizationHeaderTypeException의 힌트 테스트")
    void getHint() {
        Assertions.assertThat(new InvalidAuthorizationHeaderTypeException("jeoninpyo726").getHint()).isEqualTo("Authorization 헤더의 타입이 올바르지 않습니다. 입력한 헤더: jeoninpyo726");
    }
}