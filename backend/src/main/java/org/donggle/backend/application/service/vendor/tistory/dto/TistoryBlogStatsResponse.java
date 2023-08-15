package org.donggle.backend.application.service.vendor.tistory.dto;

public record TistoryBlogStatsResponse(
        int post,
        int comment,
        int trackback,
        int guestbook,
        int invitation
) {
}
