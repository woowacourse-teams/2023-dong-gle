package org.donggle.backend.domain.oauth;

import org.donggle.backend.application.client.LoginClient;
import org.donggle.backend.infrastructure.oauth.kakao.dto.response.UserInfo;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class LoginClients {

    private final Map<SocialType, LoginClient> clients;

    public LoginClients(final Set<LoginClient> clients) {
        final EnumMap<SocialType, LoginClient> mapping = new EnumMap<>(SocialType.class);
        clients.forEach(client -> mapping.put(client.getSocialType(), client));
        this.clients = mapping;
    }

    public String redirectUri(final SocialType socialType, final String redirectUri) {
        final LoginClient client = getClient(socialType);
        return client.createRedirectUri(redirectUri);
    }

    public UserInfo findUserInfo(final SocialType socialType, final String code, final String redirectUri) {
        final LoginClient client = getClient(socialType);
        final String accessToken = client.requestToken(code, redirectUri);
        return client.findUserInfo(accessToken);
    }

    private LoginClient getClient(final SocialType socialType) {
        return Optional.ofNullable(clients.get(socialType))
                .orElseThrow(() -> new IllegalArgumentException("해당 OAuth2 제공자는 지원되지 않습니다."));
    }
}