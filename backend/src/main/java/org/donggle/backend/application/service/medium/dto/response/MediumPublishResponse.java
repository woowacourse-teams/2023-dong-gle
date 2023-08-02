package org.donggle.backend.application.service.medium.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record MediumPublishResponse(MediumPublishDataResponse data) {
    public List<String> getTags() {
        return data.tags();
    }

    public LocalDateTime getDateTime() {
        return data.getPublishedAt();
    }
}
