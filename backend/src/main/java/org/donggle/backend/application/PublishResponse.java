package org.donggle.backend.application;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PublishResponse(
        LocalDateTime dateTime,
        List<String> tags
) {
}
