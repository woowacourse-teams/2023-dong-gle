package org.donggle.backend.application.service.request;

import java.util.List;

public record WritingsDeleteRequest(
        List<Long> writingIds,
        boolean isPermanentDelete
) {
}
