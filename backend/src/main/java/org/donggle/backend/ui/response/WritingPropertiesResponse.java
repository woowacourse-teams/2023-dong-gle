package org.donggle.backend.ui.response;

import java.time.LocalDateTime;
import java.util.List;

public record WritingPropertiesResponse(
        LocalDateTime createdAt,
        List<PublishedDetailResponse> publishedDetails
) {
}
