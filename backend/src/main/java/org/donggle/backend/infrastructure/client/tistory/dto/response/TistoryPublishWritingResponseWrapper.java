package org.donggle.backend.infrastructure.client.tistory.dto.response;

public record TistoryPublishWritingResponseWrapper(
        TistoryPublishWritingResponse tistory
) {
    public record TistoryPublishWritingResponse(
            int status,
            Long postId,
            String url
    ) {
    }
}
