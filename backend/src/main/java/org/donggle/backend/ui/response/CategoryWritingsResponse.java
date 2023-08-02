package org.donggle.backend.ui.response;

import java.util.List;

public record CategoryWritingsResponse(
        Long id,
        String categoryName,
        List<WritingSimpleResponse> writings
) {
}
