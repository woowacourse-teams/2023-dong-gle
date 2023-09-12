package org.donggle.backend.application.service;

import org.donggle.backend.application.BlogClient;
import org.donggle.backend.application.PublishResponse;
import org.donggle.backend.domain.blog.BlogType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class BlogClients {
    private final Map<BlogType, BlogClient> clients;

    public BlogClients(final Set<BlogClient> clients) {
        final EnumMap<BlogType, BlogClient> mapping = new EnumMap<>(BlogType.class);
        clients.forEach(client -> mapping.put(client.getBlogType(), client));
        this.clients = mapping;
    }

    public PublishResponse publish(
            final BlogType blogType,
            final List<String> tags,
            final String content,
            final String accessToken,
            final String titleValue
    ) {
        final BlogClient client = getClient(blogType);
        return client.publish(accessToken, content, tags, titleValue);
    }

    private BlogClient getClient(final BlogType blogType) {
        return Optional.ofNullable(clients.get(blogType))
                .orElseThrow(() -> new IllegalArgumentException("해당 블로그는 지원하지 않습니다."));
    }
}
