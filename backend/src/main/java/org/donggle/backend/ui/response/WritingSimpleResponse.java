package org.donggle.backend.ui.response;

import org.donggle.backend.domain.writing.Writing;

public record WritingSimpleResponse(Long id, String title) {
    public static WritingSimpleResponse from(final Writing writing) {
        return new WritingSimpleResponse(writing.getId(), writing.getTitleValue());
    }
}
