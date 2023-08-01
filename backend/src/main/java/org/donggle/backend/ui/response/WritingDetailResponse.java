package org.donggle.backend.ui.response;

import java.time.LocalDateTime;
import java.util.List;

public record WritingDetailResponse(
        Long id,
        String title,
        LocalDateTime createdAt,
        List<PublishedDetailResponse> publishedDetails
) {
}
