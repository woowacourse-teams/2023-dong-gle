package org.donggle.backend.ui.response;

import org.donggle.backend.domain.blog.BlogWriting;

import java.time.LocalDateTime;
import java.util.List;

public record PublishedDetailResponse(
        String blogName,
        LocalDateTime publishedAt,
        List<String> tags,
        String url
) {
    public static PublishedDetailResponse of(final BlogWriting blogWriting) {
        return new PublishedDetailResponse(blogWriting.getBlogTypeValue(), blogWriting.getPublishedAt(), blogWriting.getTags(), blogWriting.getUrl());
    }
}
