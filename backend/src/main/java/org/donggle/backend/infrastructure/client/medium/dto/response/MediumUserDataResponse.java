package org.donggle.backend.infrastructure.client.medium.dto.response;

public record MediumUserDataResponse(
        String id,
        String username,
        String name,
        String url,
        String imageUrl
) {
}
