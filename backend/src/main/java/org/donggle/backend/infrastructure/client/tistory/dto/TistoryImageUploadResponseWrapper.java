package org.donggle.backend.infrastructure.client.tistory.dto;

import org.donggle.backend.ui.response.ImageUploadResponse;

public record TistoryImageUploadResponseWrapper(
        TistoryUploadImageResponse tistory
) {
    public record TistoryUploadImageResponse(
            String url,
            String replacer
    ) {
    }

    public ImageUploadResponse toImageUploadResponse() {
        return new ImageUploadResponse(tistory.url(), tistory.replacer());
    }
}
