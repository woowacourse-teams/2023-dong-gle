package org.donggle.backend.application.service.medium;

import org.donggle.backend.application.service.medium.dto.MediumPublishRequest;
import org.donggle.backend.application.service.medium.dto.MediumPublishResponse;
import org.donggle.backend.application.service.medium.dto.MediumUserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class MediumApiService {
    private static final String MEDIUM_URL = "https://api.medium.com/v1";
    private final String mediumToken;
    private final WebClient webClient;

    public MediumApiService(@Value("${medium_token}") final String mediumToken) {
        this.mediumToken = mediumToken;
        this.webClient = WebClient.create(MEDIUM_URL);
    }

    public String getUserId() {
        final MediumUserResponse response = webClient.get()
                .uri("/me")
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .header("Authorization", "Bearer " + mediumToken)
                .retrieve()
                .bodyToMono(MediumUserResponse.class)
                .block();
        return Objects.requireNonNull(response).data().id();
    }

    public MediumPublishResponse publishContent(final MediumPublishRequest request) {
        return webClient.post()
                .uri("/users/{userId}/posts", getUserId())
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .header("Authorization", "Bearer " + mediumToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(MediumPublishResponse.class)
                .block();
    }
}
