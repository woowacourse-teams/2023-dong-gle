package org.donggle.backend.infrastructure.oauth.kakao.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.donggle.backend.domain.oauth.SocialType;

import static org.donggle.backend.infrastructure.oauth.kakao.dto.response.KakaoProfileResponse.KakaoAccount.Profile;

public record KakaoProfileResponse(
        Long id,
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount
) {

    public SocialUserInfo toUserInfo() {
        final Profile profile = kakaoAccount.profile;
        return new SocialUserInfo(
                id,
                SocialType.KAKAO,
                profile.nickname
        );
    }

    public record KakaoAccount(
            Profile profile
    ) {

        public record Profile(
                String nickname
        ) {

        }
    }
}