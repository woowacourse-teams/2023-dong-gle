package org.donggle.backend.infrastructure.client.tistory.dto.response;

import java.util.List;

public record TistoryCategoryListResponseWrapper(
        TistoryItemResponse<TistoryCategoryListResponse> tistory
) {
    public record TistoryCategoryListResponse(
            String url,
            String secondaryUrl,
            List<TistoryCategoryResponse> categories
    ) {
        public record TistoryCategoryResponse(
                String id,
                String name,
                String parent,
                String label,
                String entries
        ) {
        }
    }
}
