package org.donggle.backend.ui.response;

import org.donggle.backend.domain.category.Category;

import java.util.List;

public record WritingListWithCategoryResponse(
        Long id,
        String categoryName,
        List<WritingDetailResponse> writings
) {
    public static WritingListWithCategoryResponse of(final Category category,
                                                     final List<WritingDetailResponse> writingDetailResponses) {
        return new WritingListWithCategoryResponse(
                category.getId(),
                category.getCategoryNameValue(),
                writingDetailResponses
        );
    }
}
