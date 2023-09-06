package org.donggle.backend.application.service.vendor.tistory.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.donggle.backend.application.PublishResponse;
import org.donggle.backend.application.service.vendor.tistory.TistoryTagsDeserializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record TistoryGetWritingResponseWrapper(
        TistoryItemResponse<TistoryGetWritingResponse> tistory
) {

    public record TistoryGetWritingResponse(
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
    }

    public PublishResponse toPublishResponse() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return PublishResponse.builder()
                .bateTime(LocalDateTime.parse(tistory.item().date, formatter))
                .tags(tistory.item().tags.tags())
                .build();
    }
}
