package org.donggle.backend.ui.response;

import org.donggle.backend.domain.writing.Writing;

import java.time.LocalDateTime;
import java.util.List;

public record WritingHomeResponse(
        Long id,
        String title,
        CategoryInfo category,
        LocalDateTime createdAt,
        List<PublishedDetailResponse> publishedDetails
) {
    public static WritingHomeResponse of(final Writing writing, final List<PublishedDetailResponse> responses) {
        return new WritingHomeResponse(
                writing.getId(),
                writing.getTitleValue(),
                new CategoryInfo(
                        writing.getCategory().getId(),
                        writing.getCategory().getCategoryNameValue()
                ),
                writing.getCreatedAt(),
                responses
        );
    }

    public record CategoryInfo(Long id, String categoryName) {
    }
}
