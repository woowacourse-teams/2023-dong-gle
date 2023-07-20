package org.donggle.backend.dto;

import java.time.LocalDateTime;

public record PublishedDetailResponse(
        String blogName,
        LocalDateTime publishedAt
) {
}