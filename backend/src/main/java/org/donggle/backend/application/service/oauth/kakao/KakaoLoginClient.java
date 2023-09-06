package org.donggle.backend.application.service.oauth.kakao;

import org.donggle.backend.application.service.oauth.kakao.dto.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class KakaoLoginClient implements LoginClient {

    private final KakaoLoginTokenClient tokenClient;
    private final KakaoLoginUserInfoClient userInfoClient;

    public KakaoLoginClient(final KakaoLoginTokenClient tokenClient,
                            final KakaoLoginUserInfoClient userInfoClient
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