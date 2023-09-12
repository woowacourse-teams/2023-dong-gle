package org.donggle.backend.application.service.oauth;

import org.donggle.backend.application.service.oauth.kakao.dto.UserInfo;

public interface LoginClient {
    String createRedirectUri(final String redirectUri);

    String requestToken(final String authCode, final String redirectUri);

    UserInfo findUserInfo(final String accessToken);

    SocialType getSocialType();
}