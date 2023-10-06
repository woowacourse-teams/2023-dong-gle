package org.donggle.backend.domain.oauth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SocialTypeTest {

    @Test
    @DisplayName("socialType이 잘못 입력됐을때 예외")
    void invalidSocialTypeTest() {
        //given
        final String socialType = "KKAKKAO";

        //when
        //then
        assertThatThrownBy(
                () -> SocialType.from(socialType)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("socialType이 정상적으로 입력됐을때 테스트")
    void SocialTypeTest() {
        //given
        final String socialType = "KAKAO";

        //when
        //then
        assertDoesNotThrow(
                () -> SocialType.from(socialType)
        );
    }
}
