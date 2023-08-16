package org.donggle.backend.application.service.vendor.tistory.dto;

import org.donggle.backend.application.service.vendor.tistory.dto.response.TistoryItemResponse;

public record TistoryBlogInfoResponseWrapper(
        TistoryItemResponse<TistoryBlogInfoResponse> tistory
) {
}
