package org.donggle.backend.ui.response;

import org.donggle.backend.domain.writing.Writing;

public record TrashedWritingResponse(
        Long id,
        String title,
        Long categoryId
) {
    public static TrashedWritingResponse from(final Writing trashedWriting) {
        return new TrashedWritingResponse(
                trashedWriting.getId(),
                trashedWriting.getTitleValue(),
                trashedWriting.getCategory().getId()
        );
    }
}
