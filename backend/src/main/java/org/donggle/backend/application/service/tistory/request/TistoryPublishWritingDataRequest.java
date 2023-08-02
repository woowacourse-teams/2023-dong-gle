package org.donggle.backend.application.service.tistory.request;

import org.donggle.backend.application.service.tistory.response.TistoryPublishWritingDataResponse;

public record TistoryPublishWritingDataRequest(
        int status,
        TistoryPublishWritingDataResponse item
) {
}