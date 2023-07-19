package org.donggle.backend.application.service.medium.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record MediumPublishRequest(
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
