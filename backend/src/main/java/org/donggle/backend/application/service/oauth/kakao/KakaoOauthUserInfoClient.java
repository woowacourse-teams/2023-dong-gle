package org.donggle.backend.application.service.oauth.kakao;

import org.donggle.backend.application.service.oauth.kakao.dto.KakaoProfileResponse;
import org.donggle.backend.application.service.oauth.kakao.dto.UserInfo;
import org.donggle.backend.application.service.vendor.exception.VendorApiException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.donggle.backend.application.service.oauth.kakao.SocialType.KAKAO;

@Component
public class KakaoOauthUserInfoClient {

    private static final String PROFILE_URL = "https://kapi.kakao.com/v2/user/me";
    private final WebClient webClient;

    public KakaoOauthUserInfoClient() {
        this.webClient = WebClient.create();
    }

    public UserInfo request(final String accessToken) {
        return Objects.requireNonNull(webClient.get()
                .uri(PROFILE_URL)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> VendorApiException.handle4xxException(clientResponse.statusCode().value(), KAKAO.name()))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException(clientResponse.toString())))
                .bodyToMono(KakaoProfileResponse.class)
                .block()).toUserInfo();
    }
}