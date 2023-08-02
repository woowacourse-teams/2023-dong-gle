package org.donggle.backend.application.service.vendor.tistory.dto.request;

import org.donggle.backend.application.service.vendor.tistory.dto.response.TistoryPublishWritingDataResponse;

public record TistoryPublishWritingDataRequest(
        int status,
        TistoryPublishWritingDataResponse item
) {
}