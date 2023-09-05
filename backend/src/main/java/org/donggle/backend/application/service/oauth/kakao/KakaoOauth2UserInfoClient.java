package org.donggle.backend.application.service.oauth.kakao;

import org.donggle.backend.application.service.oauth.kakao.dto.KakaoProfileResponse;
import org.donggle.backend.application.service.oauth.kakao.dto.UserInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoOauth2UserInfoClient {

    private static final String PROFILE_URL = "https://kapi.kakao.com/v2/user/me";
    private final WebClient webClient;

    public KakaoOauth2UserInfoClient() {
        this.webClient = WebClient.create();
    }

    public UserInfo request(final String accessToken) {
        final KakaoProfileResponse kakaoProfileResponse = webClient.get()
                .uri(PROFILE_URL)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoProfileResponse.class)
                .block();
        return kakaoProfileResponse.toUserInfo();
    }
}