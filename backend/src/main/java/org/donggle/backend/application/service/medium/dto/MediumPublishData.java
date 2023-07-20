package org.donggle.backend.application.service.medium.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

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
        if (Objects.isNull(publishedAt)) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(publishedAt), ZoneId.systemDefault());
    }
}
