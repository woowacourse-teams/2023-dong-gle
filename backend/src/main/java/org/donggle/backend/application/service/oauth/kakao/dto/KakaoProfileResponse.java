package org.donggle.backend.application.service.oauth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.donggle.backend.application.service.oauth.kakao.SocialType;

import static org.donggle.backend.application.service.oauth.kakao.dto.KakaoProfileResponse.KakaoAccount.Profile;

public record KakaoProfileResponse(
        Long id,
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount
) {

    public UserInfo toUserInfo() {
        final Profile profile = kakaoAccount.profile;
        return new UserInfo(
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