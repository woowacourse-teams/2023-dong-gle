package org.donggle.backend.ui.response;

import java.time.LocalDateTime;

public record PublishedDetailResponse(
        String blogName,
        LocalDateTime publishedAt
) {
}