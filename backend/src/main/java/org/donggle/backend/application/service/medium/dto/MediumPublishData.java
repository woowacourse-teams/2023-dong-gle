package org.donggle.backend.application.service.medium.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public record MediumPublishData(
        String id,
        String title,
        String authorId,
        List<String> tags,
        String url,
        String canonicalUrl,
        String publishStatus,
        Long publishedAt,
        String license,
        String licenseUrl
) {
    public LocalDateTime getPublishedAt() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(publishedAt), ZoneId.systemDefault());
    }
}
