package org.donggle.backend.ui.response;

import java.time.LocalDateTime;
import java.util.List;

public record PublishedDetailResponse(
        String blogName,
        LocalDateTime publishedAt,
        List<String> tags
) {
}