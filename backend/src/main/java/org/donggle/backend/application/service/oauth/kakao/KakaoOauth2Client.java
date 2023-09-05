package org.donggle.backend.application.service.oauth.kakao;

import org.donggle.backend.application.service.oauth.kakao.dto.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class KakaoOauth2Client implements OauthClient {

    private final KakaoOauth2TokenClient tokenClient;
    private final KakaoOauth2UserInfoClient userInfoClient;

    public KakaoOauth2Client(final KakaoOauth2TokenClient tokenClient,
                             final KakaoOauth2UserInfoClient userInfoClient
    ) {
        this.tokenClient = tokenClient;
        this.userInfoClient = userInfoClient;
    }

    @Override
    public String createRedirectUri(final String redirectUri) {
        return tokenClient.createRedirectUri(redirectUri);
    }

    @Override
    public String requestToken(final String authCode, final String redirectUri) {
        return tokenClient.request(authCode, redirectUri);
    }

    @Override
    public UserInfo requestUserInfo(final String accessToken) {
        return userInfoClient.request(accessToken);
    }

    @Override
    public SocialType getSocialType() {
        return SocialType.KAKAO;
    }
}