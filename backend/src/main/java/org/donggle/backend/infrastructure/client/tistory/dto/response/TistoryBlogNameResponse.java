package org.donggle.backend.infrastructure.client.tistory.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.donggle.backend.infrastructure.client.tistory.dto.response.TistoryItemResponse;

import java.util.List;

public record TistoryBlogNameResponse(
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
