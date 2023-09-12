package org.donggle.backend.infrastructure.client.tistory.dto.request;

import lombok.Builder;

@Builder
public record TistoryPublishPropertyRequest(
        String access_token,
        String blogName,
        Long postId
) {
}