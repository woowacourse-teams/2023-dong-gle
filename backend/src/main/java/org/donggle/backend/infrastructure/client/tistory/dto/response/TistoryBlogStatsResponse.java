package org.donggle.backend.infrastructure.client.tistory.dto.response;

public record TistoryBlogStatsResponse(
        int post,
        int comment,
        int trackback,
        int guestbook,
        int invitation
) {
}
