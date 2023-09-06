package org.donggle.backend.application;

import org.donggle.backend.domain.blog.BlogType;

import java.util.List;

public interface BlogClient {

    PublishResponse publish(String accessToken, String content, List<String> tags, final String titleValue);

    BlogType getBlogType();
}
