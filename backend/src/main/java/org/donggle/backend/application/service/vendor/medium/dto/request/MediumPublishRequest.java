package org.donggle.backend.application.service.vendor.medium.dto.request;

public record MediumPublishRequest(
        MediumRequestHeader header,
        MediumRequestBody body
) {
}
