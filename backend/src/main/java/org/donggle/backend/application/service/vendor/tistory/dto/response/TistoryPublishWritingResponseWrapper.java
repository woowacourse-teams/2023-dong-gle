package org.donggle.backend.application.service.vendor.tistory.dto.response;

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
