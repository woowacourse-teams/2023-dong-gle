package org.donggle.backend.ui.response;

import org.donggle.backend.domain.writing.Writing;

public record WritingResponse(
        Long id,
        String title,
        String content
) {
    public static WritingResponse of(final Writing writing, final String content) {
        return new WritingResponse(writing.getId(), writing.getTitleValue(), content);
    }
}
