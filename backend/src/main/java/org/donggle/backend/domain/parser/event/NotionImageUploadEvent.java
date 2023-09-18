package org.donggle.backend.domain.parser.event;

import org.springframework.http.MediaType;

public record NotionImageUploadEvent(
        String sourceUrl,
        String key,
        MediaType mediaType
) {
}
