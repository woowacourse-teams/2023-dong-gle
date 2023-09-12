package org.donggle.backend.application.service.vendor.tistory;

import org.donggle.backend.application.BlogClient;
import org.donggle.backend.application.PublishResponse;
import org.donggle.backend.application.service.vendor.exception.VendorApiInternalServerError;
import org.donggle.backend.application.service.vendor.tistory.dto.TistoryBlogNameRespons;
import org.donggle.backend.application.service.vendor.tistory.dto.TistoryBlogNameRespons.TistoryBlogInfoResponse;
import org.donggle.backend.application.service.vendor.tistory.dto.request.TistoryPublishPropertyRequest;
import org.donggle.backend.application.service.vendor.tistory.dto.request.TistoryPublishRequest;
import org.donggle.backend.application.service.vendor.tistory.dto.response.TistoryGetWritingResponseWrapper;
import org.donggle.backend.application.service.vendor.tistory.dto.response.TistoryPublishWritingResponseWrapper;
import org.donggle.backend.domain.blog.BlogType;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.donggle.backend.application.service.vendor.exception.VendorApiException.handle4xxException;
import static org.donggle.backend.domain.blog.BlogType.TISTORY;

@Component
public class TistoryApiClient implements BlogClient {
    private static final String PLATFORM_NAME = "Tistory";
    private static final String TISTORY_URL = "https://www.tistory.com/apis";

    private final WebClient webClient;

    public TistoryApiClient() {
        this.webClient = WebClient.create(TISTORY_URL);
    }

    @Override
    public PublishResponse publish(final String accessToken, final String content, final List<String> tags, final String titleValue) {
        final TistoryPublishRequest request = makePublishRequest(accessToken, titleValue, content, tags);
        final TistoryPublishWritingResponseWrapper response = webClient.post()
                .uri("/post/write?")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(e -> new VendorApiInternalServerError(PLATFORM_NAME)))
                .bodyToMono(TistoryPublishWritingResponseWrapper.class)
                .block();
        return findPublishProperty(makeTistoryPublishPropertyRequest(accessToken, response.tistory().postId()))
                .toPublishResponse();
    }

    private TistoryPublishPropertyRequest makeTistoryPublishPropertyRequest(final String accessToken, final Long postId) {
        return TistoryPublishPropertyRequest.builder()
                .access_token(accessToken)
                .postId(postId)
                .blogName(getDefaultTistoryBlogName(accessToken))
                .build();
    }

    public TistoryPublishRequest makePublishRequest(
            final String accessToken,
            final String titleValue,
            final String content,
            final List<String> tags
    ) {
        return TistoryPublishRequest.builder()
                .access_token(accessToken)
                .blogName(getDefaultTistoryBlogName(accessToken))
                .output("json")
                .title(titleValue)
                .content(content)
                .tag(String.join(",", tags))
                .build();
    }

    public String getDefaultTistoryBlogName(final String access_token) {
        final String blogInfoUri = UriComponentsBuilder.fromUriString(TISTORY_URL)
                .path("/blog/info")
                .queryParam("access_token", access_token)
                .queryParam("output", "json")
                .build()
                .toUriString();
        final TistoryBlogNameRespons blogInfo = webClient.get()
                .uri(blogInfoUri)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(e -> new VendorApiInternalServerError(PLATFORM_NAME)))
                .bodyToMono(TistoryBlogNameRespons.class)
                .block();
        return blogInfo.tistory().item().blogs().stream()
                .filter(blog -> blog.defaultValue().equals("Y"))
                .map(TistoryBlogInfoResponse.TistoryBlogResponse::name)
                .findFirst()
                .orElseThrow();
    }

    public TistoryGetWritingResponseWrapper findPublishProperty(final TistoryPublishPropertyRequest request) {
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
                        .map(e -> new VendorApiInternalServerError(PLATFORM_NAME)))
                .bodyToMono(TistoryGetWritingResponseWrapper.class)
                .block();
    }

    @Override
    public BlogType getBlogType() {
        return TISTORY;
    }
}
