package org.donggle.backend.application.service.oauth.kakao;

import org.donggle.backend.application.service.oauth.kakao.dto.UserInfo;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class OauthClients {

    private final Map<SocialType, OauthClient> clients;

    public OauthClients(final Set<OauthClient> clients) {
        final EnumMap<SocialType, OauthClient> mapping = new EnumMap<>(SocialType.class);
        clients.forEach(client -> mapping.put(client.getSocialType(), client));
        this.clients = mapping;
    }

    public String redirectUri(final SocialType socialType, final String redirectUri) {
        final OauthClient client = getClient(socialType);
        return client.createRedirectUri(redirectUri);
    }

    public UserInfo requestUserInfo(final SocialType socialType, final String code, final String redirectUri) {
        final OauthClient client = getClient(socialType);
        final String accessToken = client.requestToken(code, redirectUri);
        return client.requestUserInfo(accessToken);
    }

    private OauthClient getClient(final SocialType socialType) {
        return Optional.ofNullable(clients.get(socialType))
                .orElseThrow(() -> new IllegalArgumentException("해당 OAuth2 제공자는 지원되지 않습니다."));
    }
}