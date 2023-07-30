package org.donggle.backend.application.service.tistory.response;

public record TistoryPublishStatusDataResponse(
        int status,
        Long postId,
        String url

) {
}