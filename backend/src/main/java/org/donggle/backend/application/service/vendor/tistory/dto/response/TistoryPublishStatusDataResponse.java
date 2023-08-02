package org.donggle.backend.application.service.vendor.tistory.dto.response;

public record TistoryPublishStatusDataResponse(
        int status,
        Long postId,
        String url
) {
}