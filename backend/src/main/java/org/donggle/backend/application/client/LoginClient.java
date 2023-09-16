package org.donggle.backend.application.client;

import org.donggle.backend.domain.oauth.SocialType;
import org.donggle.backend.infrastructure.oauth.kakao.dto.response.UserInfo;

public interface LoginClient {
    String createRedirectUri(final String redirectUri);

    String requestToken(final String authCode, final String redirectUri);

    UserInfo findUserInfo(final String accessToken);

    SocialType getSocialType();
}
