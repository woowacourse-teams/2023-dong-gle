package org.donggle.backend.application.service.vendor.tistory.dto;

import java.util.List;

public record TistoryBlogInfoResponse(
        String id,
        Long userId,
        List<TistoryBlogResponse> blogs
) {
}
