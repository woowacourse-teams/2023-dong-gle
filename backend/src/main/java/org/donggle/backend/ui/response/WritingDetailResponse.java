package org.donggle.backend.ui.response;

import org.donggle.backend.domain.writing.Writing;

import java.time.LocalDateTime;
import java.util.List;

public record WritingDetailResponse(
        Long id,
        String title,
        LocalDateTime createdAt,
        List<PublishedDetailResponse> publishedDetails
) {
    public static WritingDetailResponse of(final Writing writing, final List<PublishedDetailResponse> publishedTos) {
        return new WritingDetailResponse(
                writing.getId(),
                writing.getTitleValue(),
                writing.getCreatedAt(),
                publishedTos
        );
    }
}
