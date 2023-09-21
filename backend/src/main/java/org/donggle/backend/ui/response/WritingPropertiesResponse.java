package org.donggle.backend.ui.response;

import org.donggle.backend.domain.writing.Writing;

import java.time.LocalDateTime;
import java.util.List;

public record WritingPropertiesResponse(
        LocalDateTime createdAt,
        List<PublishedDetailSimpleResponse> publishedDetails
) {
    public static WritingPropertiesResponse of(final Writing writing,
                                               final List<PublishedDetailSimpleResponse> publishedTos) {
        return new WritingPropertiesResponse(writing.getCreatedAt(), publishedTos);
    }
}
