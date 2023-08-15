package org.donggle.backend.application.service.vendor.tistory.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record TistoryGetWritingResponseWrapper(TistoryItemResponse<TistoryGetWritingResponse> tistory) {
    public LocalDateTime getDateTime() {
        return tistory.item().getDateTime();
    }

    public List<String> getTags() {
        return tistory.item().tags().tags();
    }
}
