package org.donggle.backend.ui.response;

import org.donggle.backend.domain.category.Category;

public record CategoryResponse(Long id, String categoryName) {
    public static CategoryResponse of(final Category category) {
        return new CategoryResponse(category.getId(), category.getCategoryNameValue());
    }
}
