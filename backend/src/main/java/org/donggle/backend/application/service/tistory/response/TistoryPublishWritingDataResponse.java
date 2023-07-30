package org.donggle.backend.application.service.tistory.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.donggle.backend.application.service.tistory.TistoryTagsDeserializer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
        TistoryTags tags
) {
    public LocalDateTime getDateTime() {
        long timestamp = Long.parseLong(date);
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
    }
}
