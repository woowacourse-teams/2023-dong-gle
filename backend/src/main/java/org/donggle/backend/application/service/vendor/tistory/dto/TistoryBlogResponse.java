package org.donggle.backend.application.service.vendor.tistory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TistoryBlogResponse(
        String name,
        String url,
        String secondaryUrl,
        String nickname,
        String title,
        String description,
        @JsonProperty("default")
        String defaultValue,
        String blogIconUrl,
        String faviconUrl,
        String profileThumbnailImageUrl,
        String profileImageUrl,
        String role,
        TistoryBlogStatsResponse stats
) {
}
