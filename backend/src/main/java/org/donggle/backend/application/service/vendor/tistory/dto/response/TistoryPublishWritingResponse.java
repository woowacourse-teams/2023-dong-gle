package org.donggle.backend.application.service.vendor.tistory.dto.response;

import org.donggle.backend.application.service.vendor.tistory.dto.request.TistoryPublishWritingDataRequest;

import java.time.LocalDateTime;
import java.util.List;

public record TistoryPublishWritingResponse(TistoryPublishWritingDataRequest tistory) implements TistoryResponse {
    @Override
    public int getStatus() {
        return tistory.status();
    }

    public LocalDateTime getDateTime() {
        return tistory.item().getDateTime();
    }

    public List<String> getTags() {
        return tistory.item().tags().tags();
    }
}
