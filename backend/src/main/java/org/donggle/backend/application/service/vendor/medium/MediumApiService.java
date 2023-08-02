package org.donggle.backend.application.service.vendor.medium;

import org.donggle.backend.application.service.vendor.exception.VendorApiException;
import org.donggle.backend.application.service.vendor.exception.VendorApiInternalServerError;
import org.donggle.backend.application.service.vendor.medium.dto.request.MediumPublishRequest;
import org.donggle.backend.application.service.vendor.medium.dto.request.MediumRequestBody;
import org.donggle.backend.application.service.vendor.medium.dto.response.MediumPublishResponse;
import org.donggle.backend.application.service.vendor.medium.dto.response.MediumUserResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class MediumApiService {
    public static final String PLATFORM_NAME = "Medium";
    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";
    private static final String MEDIUM_URL = "https://api.medium.com/v1";

    private final WebClient webClient;

    public MediumApiService() {
        this.webClient = WebClient.create(MEDIUM_URL);
    }

    public MediumPublishResponse publishContent(final MediumPublishRequest request) {
        final String mediumToken = request.header().token();
        final MediumRequestBody body = request.body();
        return webClient.post()
                .uri("/users/{userId}/posts", getUserId(mediumToken))
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .header(AUTHORIZATION, BEARER + mediumToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> VendorApiException.handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(e -> new VendorApiInternalServerError(PLATFORM_NAME)))
                .bodyToMono(MediumPublishResponse.class)
                .block();
    }

    public String getUserId(final String mediumToken) {
        final MediumUserResponse response = webClient.get()
                .uri("/me")
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .header(AUTHORIZATION, BEARER + mediumToken)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> VendorApiException.handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                .bodyToMono(MediumUserResponse.class)
                .block();
        return Objects.requireNonNull(response).data().id();
    }
}
