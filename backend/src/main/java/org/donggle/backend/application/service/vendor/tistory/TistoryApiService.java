package org.donggle.backend.application.service.vendor.tistory;

import org.donggle.backend.application.service.vendor.exception.VendorApiInternalServerError;
import org.donggle.backend.application.service.vendor.tistory.dto.TistoryBlogInfoResponseWrapper;
import org.donggle.backend.application.service.vendor.tistory.dto.TistoryBlogResponse;
import org.donggle.backend.application.service.vendor.tistory.dto.request.TistoryPublishPropertyRequest;
import org.donggle.backend.application.service.vendor.tistory.dto.request.TistoryPublishRequest;
import org.donggle.backend.application.service.vendor.tistory.dto.response.TistoryGetWritingResponseWrapper;
import org.donggle.backend.application.service.vendor.tistory.dto.response.TistoryPublishWritingResponseWrapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import static org.donggle.backend.application.service.vendor.exception.VendorApiException.handle4xxException;

public class TistoryApiService {
    public static final String PLATFORM_NAME = "Tistory";
    private static final String TISTORY_URL = "https://www.tistory.com/apis";
    private static final int OK = 200;
    private final WebClient webClient;

    public TistoryApiService() {
        this.webClient = WebClient.create(TISTORY_URL);
    }

    public TistoryGetWritingResponseWrapper publishContent(final TistoryPublishRequest request) {
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
        return findPublishProperty(makeTistoryPublishPropertyRequest(request, response));
    }

    private TistoryPublishPropertyRequest makeTistoryPublishPropertyRequest(final TistoryPublishRequest request, final TistoryPublishWritingResponseWrapper response) {
        return TistoryPublishPropertyRequest.builder()
                .access_token(request.access_token())
                .postId(response.tistory().postId())
                .blogName(getDefaultTistoryBlogName(request.access_token()))
                .build();
    }

    public String getDefaultTistoryBlogName(final String access_token) {
        final String blogInfoUri = UriComponentsBuilder.fromUriString(TISTORY_URL)
                .path("/blog/info")
                .queryParam("access_token", access_token)
                .queryParam("output", "json")
                .build()
                .toUriString();
        final TistoryBlogInfoResponseWrapper BlogInfo = webClient.get()
                .uri(blogInfoUri)
                .retrieve()
                .bodyToMono(TistoryBlogInfoResponseWrapper.class)
                .block();
        return BlogInfo.tistory().item().blogs().stream()
                .filter(blog -> blog.defaultValue().equals("Y"))
                .map(TistoryBlogResponse::name)
                .findFirst()
                .orElseThrow();
    }

    public TistoryGetWritingResponseWrapper findPublishProperty(final TistoryPublishPropertyRequest request) {
        return webClient.get()
                .uri("/post/read?" +
                        "access_token=" + request.access_token() +
                        "&blogName=" + request.blogName() +
                        "&postId=" + request.postId() +
                        "&output=json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(e -> new VendorApiInternalServerError(PLATFORM_NAME)))
                .bodyToMono(TistoryGetWritingResponseWrapper.class)
                .block();
    }
}
