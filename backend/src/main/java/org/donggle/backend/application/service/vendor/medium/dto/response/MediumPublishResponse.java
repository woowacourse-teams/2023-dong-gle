package org.donggle.backend.application.service.vendor.medium.dto.response;

import org.donggle.backend.application.PublishResponse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public record MediumPublishResponse(
        MediumPublishDataResponse data
) {
    public record MediumPublishDataResponse(
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
        public PublishResponse toPublishResponse() {
            return PublishResponse.builder()
                    .dateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(publishedAt), ZoneId.systemDefault()))
                    .tags(tags)
                    .build();
        }
    }
}
