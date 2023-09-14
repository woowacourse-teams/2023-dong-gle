package org.donggle.backend.application.service.blog;

import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.blog.BlogWriting;
import org.donggle.backend.domain.writing.Writing;

import java.util.List;

public record PublishWritingRequest(
        Blog blog,
        List<BlogWriting> publishedBlogs,
        Writing writing,
        String accessToken
) {
}
