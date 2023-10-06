package org.donggle.backend.ui.response;

import java.util.List;

public record TistoryCategoryListResponse(
        List<TistoryCategoryResponse> categories
) {
    public record TistoryCategoryResponse(
            String id,
            String name
    ) {
    }
}
