package org.donggle.backend.infrastructure.client.tistory;

import org.donggle.backend.application.client.BlogClient;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.domain.blog.PublishStatus;
import org.donggle.backend.infrastructure.client.exception.ClientInternalServerError;
import org.donggle.backend.infrastructure.client.tistory.dto.request.TistoryPublishPropertyRequest;
import org.donggle.backend.infrastructure.client.tistory.dto.request.TistoryPublishRequest;
import org.donggle.backend.infrastructure.client.tistory.dto.response.TistoryBlogNameResponse;
import org.donggle.backend.infrastructure.client.tistory.dto.response.TistoryGetWritingResponseWrapper;
import org.donggle.backend.infrastructure.client.tistory.dto.response.TistoryPublishWritingResponseWrapper;
import org.donggle.backend.ui.response.PublishResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.donggle.backend.domain.blog.BlogType.TISTORY;
import static org.donggle.backend.infrastructure.client.exception.ClientException.handle4xxException;

@Component
public class TistoryApiClient implements BlogClient {
    private static final String PLATFORM_NAME = "Tistory";
    private static final String TISTORY_URL = "https://www.tistory.com/apis";

    private final WebClient webClient;

    public TistoryApiClient() {
        this.webClient = WebClient.create(TISTORY_URL);
    }

    @Override
    public PublishResponse publish(final String accessToken, final String content, final PublishRequest publishRequest, final String titleValue) {
        final TistoryPublishRequest request = makePublishRequest(accessToken, titleValue, content, publishRequest);
        final TistoryPublishWritingResponseWrapper response = webClient.post()
                .uri("/post/write?")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(e -> new ClientInternalServerError(PLATFORM_NAME)))
                .bodyToMono(TistoryPublishWritingResponseWrapper.class)
                .block();
        return findPublishProperty(makeTistoryPublishPropertyRequest(accessToken, response.tistory().postId()))
                .toPublishResponse();
    }

    private TistoryPublishPropertyRequest makeTistoryPublishPropertyRequest(final String accessToken, final Long postId) {
        return TistoryPublishPropertyRequest.builder()
                .access_token(accessToken)
                .postId(postId)
                .blogName(findDefaultBlogName(accessToken))
                .build();
    }

    private TistoryPublishRequest makePublishRequest(
            final String accessToken,
            final String titleValue,
            final String content,
            final PublishRequest publishRequest
    ) {
        final PublishStatus publishStatus = PublishStatus.from(publishRequest.publishStatus());
        if (publishStatus == PublishStatus.PROTECT) {
            return TistoryPublishRequest.builder()
                    .access_token(accessToken)
                    .blogName(findDefaultBlogName(accessToken))
                    .output("json")
                    .title(titleValue)
                    .content(content)
                    .visibility(publishStatus.getTistory())
                    .category(publishRequest.categoryId())
                    .tag(String.join(",", publishRequest.tags()))
                    .published(makePublished(publishRequest.publishTime()))
                    .password(publishRequest.password())
                    .build();
        }
        return TistoryPublishRequest.builder()
                .access_token(accessToken)
                .blogName(findDefaultBlogName(accessToken))
                .output("json")
                .title(titleValue)
                .content(content)
                .visibility(publishStatus.getTistory())
                .category(publishRequest.categoryId())
                .tag(String.join(",", publishRequest.tags()))
                .published(makePublished(publishRequest.publishTime()))
                .build();
    }

    private String makePublished(final String publishTime) {
        if (publishTime.isBlank()) {
            return String.valueOf(LocalDateTime.now());
        }
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        final LocalDateTime publishLocalDateTime = LocalDateTime.parse(publishTime, formatter);
        if (publishLocalDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("현재 시간보다 과거의 시간은 입력될 수 없습니다.");
        }
        return publishTime;
    }

    public String findDefaultBlogName(final String access_token) {
        final String blogInfoUri = UriComponentsBuilder.fromUriString(TISTORY_URL)
                .path("/blog/info")
                .queryParam("access_token", access_token)
                .queryParam("output", "json")
                .build()
                .toUriString();
        final TistoryBlogNameResponse blogInfo = webClient.get()
                .uri(blogInfoUri)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(e -> new ClientInternalServerError(PLATFORM_NAME)))
                .bodyToMono(TistoryBlogNameResponse.class)
                .block();
        return blogInfo.tistory().item().blogs().stream()
                .filter(blog -> blog.defaultValue().equals("Y"))
                .map(TistoryBlogNameResponse.TistoryBlogInfoResponse.TistoryBlogResponse::name)
                .findFirst()
                .orElseThrow();
    }

    private TistoryGetWritingResponseWrapper findPublishProperty(final TistoryPublishPropertyRequest request) {
        final String publishPropertyUri = UriComponentsBuilder.fromUriString("/post/read")
                .queryParam("access_token", request.access_token())
                .queryParam("blogName", request.blogName())
                .queryParam("postId", request.postId())
                .queryParam("output", "json")
                .build()
                .toUriString();
        return webClient.get()
                .uri(publishPropertyUri)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(e -> new ClientInternalServerError(PLATFORM_NAME)))
                .bodyToMono(TistoryGetWritingResponseWrapper.class)
                .block();
    }

    @Override
    public BlogType getBlogType() {
        return TISTORY;
    }
}
