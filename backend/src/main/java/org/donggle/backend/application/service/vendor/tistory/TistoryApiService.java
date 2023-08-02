package org.donggle.backend.application.service.vendor.tistory;

import org.donggle.backend.application.service.vendor.exception.VendorApiInternalServerError;
import org.donggle.backend.application.service.vendor.tistory.dto.request.TistoryPublishPropertyRequest;
import org.donggle.backend.application.service.vendor.tistory.dto.request.TistoryPublishRequest;
import org.donggle.backend.application.service.vendor.tistory.dto.response.TistoryPublishStatusResponse;
import org.donggle.backend.application.service.vendor.tistory.dto.response.TistoryPublishWritingResponse;
import org.donggle.backend.application.service.vendor.tistory.dto.response.TistoryResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import static org.donggle.backend.application.service.vendor.exception.VendorApiException.handle4xxException;

public class TistoryApiService {
    public static final String PLATFORM_NAME = "Tistory";
    private static final String TISTORY_URL = "https://www.tistory.com/apis";
    private static final int OK = 200;
    private static final int BAD_REQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;

    private final WebClient webClient;

    public TistoryApiService() {
        this.webClient = WebClient.create(TISTORY_URL);
    }

    public TistoryPublishWritingResponse publishContent(final TistoryPublishRequest request) {
        final TistoryPublishStatusResponse response = webClient.post()
                .uri("/post/write?")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(e -> new VendorApiInternalServerError(PLATFORM_NAME)))
                .bodyToMono(TistoryPublishStatusResponse.class)
                .block();
        validateResponse(response);

        return findPublishProperty(makeTistoryPublishPropertyRequest(request, response));
    }

    private TistoryPublishPropertyRequest makeTistoryPublishPropertyRequest(final TistoryPublishRequest request, final TistoryPublishStatusResponse response) {
        return TistoryPublishPropertyRequest.builder()
                .access_token(request.access_token())
                .postId(response.tistory().postId())
                .blogName(request.blogName())
                .build();
    }

    private void validateResponse(final TistoryResponse response) {
        if (response.getStatus() != OK) {
            throw new IllegalArgumentException("블로그로 발행이 올바르게 되지 않았습니다.");
        }
    }

    public TistoryPublishWritingResponse findPublishProperty(final TistoryPublishPropertyRequest request) {
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
                .bodyToMono(TistoryPublishWritingResponse.class)
                .block();
    }
}
