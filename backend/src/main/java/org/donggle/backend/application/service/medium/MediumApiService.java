package org.donggle.backend.application.service.medium;

import org.donggle.backend.application.service.medium.dto.request.MediumPublishRequest;
import org.donggle.backend.application.service.medium.dto.request.MediumRequestBody;
import org.donggle.backend.application.service.medium.dto.response.MediumPublishResponse;
import org.donggle.backend.application.service.medium.dto.response.MediumUserResponse;
import org.donggle.backend.application.service.medium.exception.MediumBadRequestException;
import org.donggle.backend.application.service.medium.exception.MediumForbiddenException;
import org.donggle.backend.application.service.medium.exception.MediumInternalServerError;
import org.donggle.backend.application.service.medium.exception.MediumUnAuthorizedException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class MediumApiService {
    private static final int BAD_REQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
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
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handle4xxException(clientResponse.statusCode().value()))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> clientResponse.bodyToMono(String.class).map(e -> new MediumInternalServerError()))
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
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handle4xxException(clientResponse.statusCode().value()))
                .bodyToMono(MediumUserResponse.class)
                .block();
        return Objects.requireNonNull(response).data().id();
    }

    private Mono<? extends Throwable> handle4xxException(final int code) {
        return switch (code) {
            case BAD_REQUEST -> Mono.error(new MediumBadRequestException());
            case UNAUTHORIZED -> Mono.error(new MediumUnAuthorizedException());
            case FORBIDDEN -> Mono.error(new MediumForbiddenException());
            default -> Mono.error(new RuntimeException());
        };
    }
}
