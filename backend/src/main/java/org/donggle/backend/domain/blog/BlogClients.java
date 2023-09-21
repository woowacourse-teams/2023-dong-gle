package org.donggle.backend.domain.blog;

import org.donggle.backend.application.client.BlogClient;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.ui.response.PublishResponse;

import java.util.EnumMap;
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
            final PublishRequest publishRequest,
            final String content,
            final String accessToken,
            final String titleValue
    ) {
        final BlogClient client = getClient(blogType);
        return client.publish(accessToken, content, publishRequest, titleValue);
    }

    private BlogClient getClient(final BlogType blogType) {
        return Optional.ofNullable(clients.get(blogType))
                .orElseThrow(() -> new IllegalArgumentException("해당 블로그는 지원하지 않습니다."));
    }
}
