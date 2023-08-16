package org.donggle.backend.application.service.request;

import java.util.List;

public record DeleteWritingsRequest(
        List<Long> writingIds,
        boolean isPermanentDelete
) {
}
