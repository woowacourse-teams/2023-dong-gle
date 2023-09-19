package org.donggle.backend.ui.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PublishResponse(
        LocalDateTime dateTime,
        List<String> tags,
        String url
) {
}
