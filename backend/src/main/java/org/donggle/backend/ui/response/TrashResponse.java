package org.donggle.backend.ui.response;

import org.donggle.backend.domain.writing.Writing;

import java.util.List;

public record TrashResponse(
        List<TrashedWritingResponse> writings
) {
    public static TrashResponse from(final List<Writing> trashedWritings) {
        return new TrashResponse(
                trashedWritings.stream()
                        .map(TrashedWritingResponse::from)
                        .toList()
        );
    }
}
