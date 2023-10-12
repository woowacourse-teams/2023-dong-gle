package org.donggle.backend.application.service.publish;

import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.writing.Writing;

public record PublishWritingRequest(
        Blog blog,
        Writing writing,
        String accessToken
) {
}
