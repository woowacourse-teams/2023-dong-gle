package org.donggle.backend.ui.response;

import org.donggle.backend.domain.blog.BlogWriting;

import java.time.LocalDateTime;

public record PublishedDetailSimpleResponse(
        String blogName,
        LocalDateTime publishedAt
) {
    public static PublishedDetailSimpleResponse of(final BlogWriting blogWriting) {
        return new PublishedDetailSimpleResponse(blogWriting.getBlogTypeValue(), blogWriting.getPublishedAt());
    }
}
