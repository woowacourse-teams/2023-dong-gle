package org.donggle.backend.application.service.medium.dto;

import java.time.LocalDate;
import java.util.List;

public record MediumPublishData(
        String id,
        String title,
        String authorId,
        List<String> tags,
        String url,
        String canonicalUrl,
        String publishStatus,
        LocalDate publishedAt,
        String license,
        String licenseUrl
) {
}
