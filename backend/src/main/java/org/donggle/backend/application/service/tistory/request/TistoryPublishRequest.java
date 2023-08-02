package org.donggle.backend.application.service.tistory.request;

import lombok.Builder;

import java.util.List;

@Builder
public record TistoryPublishRequest(
        String access_token,
        String output,
        String blogName,
        String title,
        String content,
        int visibility,
        int category,
        String published,
        List<String> slogan,
        String tag,
        String acceptComment,
        String password
) {
}
