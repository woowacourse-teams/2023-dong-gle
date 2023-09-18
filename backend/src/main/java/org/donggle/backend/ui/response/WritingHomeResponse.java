package org.donggle.backend.ui.response;

import org.donggle.backend.domain.writing.Writing;

import java.util.List;

public record WritingHomeResponse(
        Long id,
        String title,
        CategoryInfo category,
        List<PublishedDetailResponse> publish
) {
    public static WritingHomeResponse of(final Writing writing, final List<PublishedDetailResponse> responses) {
        return new WritingHomeResponse(
                writing.getId(),
                writing.getTitleValue(),
                new CategoryInfo(
                        writing.getCategory().getId(),
                        writing.getCategory().getCategoryNameValue()
                ),
                responses
        );
    }

    public record CategoryInfo(Long id, String categoryName) {
    }
}
