package org.donggle.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public record WritingPropertiesResponse(
        LocalDateTime createdAt,
        List<PublishedDetailResponse> publishedDetails
) {
}
