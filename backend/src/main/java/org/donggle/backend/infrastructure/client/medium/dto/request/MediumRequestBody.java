package org.donggle.backend.infrastructure.client.medium.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record MediumRequestBody(
        String title,
        String contentFormat,
        String content,
        String canonicalUrl,
        String publishStatus,
        String license,
        String notifyFollowers,
        List<String> tags
) {
}
