package org.donggle.backend.application.service.vendor.medium.dto.response;

public record MediumUserDataResponse(
        String id,
        String username,
        String name,
        String url,
        String imageUrl
) {
}
