package org.donggle.backend.application.service.tistory;

import org.donggle.backend.application.service.medium.exception.MediumBadRequestException;
import org.donggle.backend.application.service.medium.exception.MediumForbiddenException;
import org.donggle.backend.application.service.medium.exception.MediumInternalServerError;
import org.donggle.backend.application.service.medium.exception.MediumUnAuthorizedException;
import org.donggle.backend.application.service.tistory.request.TistoryPublishPropertyRequest;
import org.donggle.backend.application.service.tistory.request.TistoryPublishRequest;
import org.donggle.backend.application.service.tistory.response.TistoryPublishStatusResponse;
import org.donggle.backend.application.service.tistory.response.TistoryPublishWritingResponse;
import org.donggle.backend.application.service.tistory.response.TistoryResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TistoryApiService {
    public static final String TISTORY_URL = "https://www.tistory.com/apis";
    public static final int OK = 200;
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
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handle4xxException(clientResponse.statusCode().value()))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> clientResponse.bodyToMono(String.class).map(e -> new MediumInternalServerError()))
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

    private Mono<? extends Throwable> handle4xxException(final int code) {
        return switch (code) {
            case BAD_REQUEST -> Mono.error(new MediumBadRequestException());
            case UNAUTHORIZED -> Mono.error(new MediumUnAuthorizedException());
            case FORBIDDEN -> Mono.error(new MediumForbiddenException());
            default -> Mono.error(new RuntimeException());
        };
    }

    private void validateResponse(final TistoryResponse response) {
        if (response.getStatus() != OK) {
            throw new IllegalArgumentException("블로그로 발행이 올바르게 되지 않았습니다.");
        }
    }

    public TistoryPublishWritingResponse findPublishProperty(final TistoryPublishPropertyRequest request) {
        final TistoryPublishWritingResponse response = webClient.get()
                .uri("/post/read?" +
                        "access_token=" + request.access_token() +
                        "&blogName=" + request.blogName() +
                        "&postId=" + request.postId() +
                        "&output=json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handle4xxException(clientResponse.statusCode().value()))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> clientResponse.bodyToMono(String.class).map(e -> new MediumInternalServerError()))
                .bodyToMono(TistoryPublishWritingResponse.class)
                .block();
        return response;
    }
}
