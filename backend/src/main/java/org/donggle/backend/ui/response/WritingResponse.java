package org.donggle.backend.ui.response;

public record WritingResponse(
        Long id,
        String title,
        String content
) {
}
