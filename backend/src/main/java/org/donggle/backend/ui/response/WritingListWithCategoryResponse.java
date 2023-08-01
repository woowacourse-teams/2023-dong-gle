package org.donggle.backend.ui.response;

import java.util.List;

public record WritingListWithCategoryResponse(
        Long id,
        String categoryName,
        List<WritingDetailResponse> writings
) {
}
