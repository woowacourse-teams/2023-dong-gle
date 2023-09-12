package org.donggle.backend.infrastructure.client.medium.dto.request;

public record MediumPublishRequest(
        MediumRequestHeader header,
        MediumRequestBody body
) {
}
