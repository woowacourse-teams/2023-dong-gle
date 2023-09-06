package org.donggle.backend.application.service.vendor.medium;

import org.donggle.backend.application.BlogClient;
import org.donggle.backend.application.PublishResponse;
import org.donggle.backend.application.service.vendor.exception.VendorApiException;
import org.donggle.backend.application.service.vendor.exception.VendorApiInternalServerError;
import org.donggle.backend.application.service.vendor.medium.dto.request.MediumRequestBody;
import org.donggle.backend.application.service.vendor.medium.dto.response.MediumPublishResponse;
import org.donggle.backend.application.service.vendor.medium.dto.response.MediumUserResponse;
import org.donggle.backend.domain.blog.BlogType;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.List;
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

    @Override
    public PublishResponse publish(final String accessToken, final String content, final List<String> tags, final String titleValue) {
        final MediumRequestBody body = makePublishRequest(titleValue, content, tags);
        return webClient.post()
                .uri("/users/{userId}/posts", getUserId(accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .header(AUTHORIZATION, BEARER + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> VendorApiException.handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(e -> new VendorApiInternalServerError(PLATFORM_NAME)))
                .bodyToMono(MediumPublishResponse.class)
                .block().data().toPublishResponse();
    }

    private MediumRequestBody makePublishRequest(final String titleValue, final String content, final List<String> tags) {
        return MediumRequestBody.builder()
                .title(titleValue)
                .content(content)
                .tags(tags)
                .build();
    }

    private String getUserId(final String mediumToken) {
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

    @Override
    public BlogType getBlogType() {
        return MEDIUM;
    }
}
