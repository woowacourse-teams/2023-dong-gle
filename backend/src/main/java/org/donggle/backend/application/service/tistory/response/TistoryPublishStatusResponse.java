package org.donggle.backend.application.service.tistory.response;

public record TistoryPublishStatusResponse(TistoryPublishStatusDataResponse tistory) implements TistoryResponse {
    @Override
    public int getStatus() {
        return tistory.status();
    }
}
