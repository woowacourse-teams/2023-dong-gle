package org.donggle.backend.application.client;

import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.ui.response.PublishResponse;

import java.util.List;

public interface BlogClient {

    PublishResponse publish(String accessToken, String content, List<String> tags, final String titleValue);

    BlogType getBlogType();
}
