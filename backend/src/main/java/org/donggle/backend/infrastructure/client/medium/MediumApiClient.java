package org.donggle.backend.infrastructure.client.medium;

import org.donggle.backend.application.client.BlogClient;
import org.donggle.backend.application.service.request.ImageUploadRequest;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.domain.blog.PublishStatus;
import org.donggle.backend.infrastructure.client.exception.ClientException;
import org.donggle.backend.infrastructure.client.exception.ClientInternalServerError;
import org.donggle.backend.infrastructure.client.medium.dto.request.MediumRequestBody;
import org.donggle.backend.infrastructure.client.medium.dto.response.MediumPublishResponse;
import org.donggle.backend.infrastructure.client.medium.dto.response.MediumUserResponse;
import org.donggle.backend.ui.response.ImageUploadResponse;
import org.donggle.backend.ui.response.PublishResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.donggle.backend.domain.blog.BlogType.MEDIUM;

@Component
public class MediumApiClient implements BlogClient {
    public static final String PLATFORM_NAME = "Medium";
    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";
    private static final String MEDIUM_URL = "https://api.medium.com/v1";

    private final WebClient webClient;

    public MediumApiClient() {
        this.webClient = WebClient.create(MEDIUM_URL);
    }

    public MediumApiClient(final WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public PublishResponse publish(final String accessToken, final String content, final PublishRequest publishRequest, final String titleValue) {
        final MediumRequestBody body = makePublishRequest(titleValue, content, publishRequest);
        return webClient.post()
                .uri("/users/{userId}/posts", getUserId(accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .header(AUTHORIZATION, BEARER + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> ClientException.handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(e -> new ClientInternalServerError(PLATFORM_NAME)))
                .bodyToMono(MediumPublishResponse.class)
                .block().data().toPublishResponse();
    }

    @Override
    public ImageUploadResponse uploadImage(final String accessToken, final ImageUploadRequest imageUploadRequest) {
        return null;
    }

    private MediumRequestBody makePublishRequest(final String titleValue, final String content, final PublishRequest publishRequest) {
        return MediumRequestBody.builder()
                .title(titleValue)
                .content(content)
                .contentFormat("html")
                .tags(publishRequest.tags())
                .publishStatus(PublishStatus.from(publishRequest.publishStatus()).getMedium())
                .build();
    }

    private String getUserId(final String mediumToken) {
        final MediumUserResponse response = webClient.get()
                .uri("/me")
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .header(AUTHORIZATION, BEARER + mediumToken)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> ClientException.handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                .bodyToMono(MediumUserResponse.class)
                .block();
        return Objects.requireNonNull(response).data().id();
    }

    @Override
    public BlogType getBlogType() {
        return MEDIUM;
    }
}
