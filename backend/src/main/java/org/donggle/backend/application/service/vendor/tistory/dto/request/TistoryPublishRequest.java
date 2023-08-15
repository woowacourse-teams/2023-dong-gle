package org.donggle.backend.application.service.vendor.tistory.dto.request;

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
        int category,
        String published,
        List<String> slogan,
        String tag,
        String acceptComment,
        String password
) {
}
