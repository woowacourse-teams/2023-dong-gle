package org.donggle.backend.infrastructure.client.tistory.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record TistoryPublishRequest(
        String access_token,
        String blogName,
        String output,
        String title,
        String content,
        int visibility,
        Long category,
        String published,
        List<String> slogan,
        String tag,
        String acceptComment,
        String password
) {
}
