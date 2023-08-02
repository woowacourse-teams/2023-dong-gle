package org.donggle.backend.application.service.vendor.tistory.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.donggle.backend.application.service.vendor.tistory.TistoryTagsDeserializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record TistoryPublishWritingDataResponse(
        String url,
        String secondaryUrl,
        String id,
        String slogan,
        String title,
        String content,
        String categoryId,
        String postUrl,
        String visibility,
        String acceptComment,
        String acceptTrackback,
        String comments,
        String trackbacks,
        String date,
        @JsonDeserialize(using = TistoryTagsDeserializer.class)
        TistoryTagsResponse tags
) {
    public LocalDateTime getDateTime() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }
}
