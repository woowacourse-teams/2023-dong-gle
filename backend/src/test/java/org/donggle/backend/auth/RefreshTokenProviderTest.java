package org.donggle.backend.auth;

import org.donggle.backend.domain.auth.RefreshToken;
import org.donggle.backend.support.fix.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RefreshTokenProviderTest {


    @Test
    @DisplayName("refreshToken이 일치하는지 테스트")
    void equalRefreshToken() {
        //given
        final RefreshToken refreshToken = new RefreshToken("jeoninpyo726", MemberFixture.beaver_have_id);
        //when

        //then
        assertThat(refreshToken.isDifferentFrom("ingpyo")).isTrue();
        assertThat(refreshToken.isDifferentFrom("jeoninpyo726")).isFalse();
    }

    @Test
    @DisplayName("refreshToken이 갱신이 되는지 테스트")
    void updateRefreshToken() {
        //given
        final RefreshToken refreshToken = new RefreshToken("jeoninpyo726", MemberFixture.beaver_have_id);

        //when
        refreshToken.update("ingpyo");

        //then
        assertThat(refreshToken.getRefreshToken()).isEqualTo("ingpyo");
    }
}
