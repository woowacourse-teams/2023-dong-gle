package org.donggle.backend.ui.response;

import org.donggle.backend.domain.blog.BlogWriting;

import java.time.LocalDateTime;

public record PublishedDetailResponse(
        String blogName,
        LocalDateTime publishedAt
) {
    public static PublishedDetailResponse of(final BlogWriting blogWriting) {
        return new PublishedDetailResponse(blogWriting.getBlogTypeValue(), blogWriting.getPublishedAt());
    }
}
