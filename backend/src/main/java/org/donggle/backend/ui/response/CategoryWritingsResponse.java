package org.donggle.backend.ui.response;

import org.donggle.backend.domain.category.Category;

import java.util.List;

public record CategoryWritingsResponse(
        Long id,
        String categoryName,
        List<WritingSimpleResponse> writings
) {
    public static CategoryWritingsResponse of(final Category findCategory,
                                              final List<WritingSimpleResponse> writingSimpleResponses
    ) {
        return new CategoryWritingsResponse(
                findCategory.getId(),
                findCategory.getCategoryNameValue(),
                writingSimpleResponses
        );
    }
}
