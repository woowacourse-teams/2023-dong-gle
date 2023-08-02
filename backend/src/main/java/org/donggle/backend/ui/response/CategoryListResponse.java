package org.donggle.backend.ui.response;

import java.util.List;

public record CategoryListResponse(List<CategoryResponse> categories) {
    public static CategoryListResponse from(final List<CategoryResponse> categoryResponses) {
        return new CategoryListResponse(categoryResponses);
    }
}
