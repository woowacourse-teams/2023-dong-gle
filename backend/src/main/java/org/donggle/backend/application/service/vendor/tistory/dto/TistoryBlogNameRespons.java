package org.donggle.backend.application.service.vendor.tistory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.donggle.backend.application.service.vendor.tistory.dto.response.TistoryItemResponse;

import java.util.List;

public record TistoryBlogNameRespons(
        TistoryItemResponse<TistoryBlogInfoResponse> tistory
) {

    public record TistoryBlogInfoResponse(
            List<TistoryBlogResponse> blogs
    ) {

        public record TistoryBlogResponse(
                String name,
                @JsonProperty("default")
                String defaultValue
        ) {
        }
    }
}
