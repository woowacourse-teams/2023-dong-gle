package org.donggle.backend.infrastructure.oauth.kakao;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.client.LoginClient;
import org.donggle.backend.domain.oauth.SocialType;
import org.donggle.backend.infrastructure.oauth.kakao.dto.response.UserInfo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoLoginClient implements LoginClient {

    private final KakaoLoginTokenClient tokenClient;
    private final KakaoLoginUserInfoClient userInfoClient;

    @Override
    public String createRedirectUri(final String redirectUri) {
        return tokenClient.createRedirectUri(redirectUri);
    }

    @Override
    public String requestToken(final String authCode, final String redirectUri) {
        return tokenClient.request(authCode, redirectUri);
    }

    @Override
    public UserInfo findUserInfo(final String accessToken) {
        return userInfoClient.request(accessToken);
    }

    @Override
    public SocialType getSocialType() {
        return SocialType.KAKAO;
    }
}