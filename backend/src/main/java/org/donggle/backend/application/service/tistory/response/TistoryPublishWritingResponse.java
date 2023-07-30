package org.donggle.backend.application.service.tistory.response;

import org.donggle.backend.application.service.tistory.request.TistoryPublishWritingDataRequest;

public record TistoryPublishWritingResponse(TistoryPublishWritingDataRequest tistory) implements TistoryResponse {
}
