package org.donggle.backend.application.client;

import org.donggle.backend.domain.oauth.SocialType;
import org.donggle.backend.infrastructure.oauth.kakao.dto.response.SocialUserInfo;

public interface LoginClient {
    String createRedirectUri(final String redirectUri);

    String requestToken(final String authCode, final String redirectUri);

    SocialUserInfo findUserInfo(final String accessToken);

    SocialType getSocialType();
}
