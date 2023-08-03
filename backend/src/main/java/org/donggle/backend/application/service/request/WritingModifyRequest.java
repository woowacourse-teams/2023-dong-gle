package org.donggle.backend.application.service.request;

public record WritingModifyRequest(
        String title,
        Long targetCategoryId,
        Long nextWritingId
) {
}
