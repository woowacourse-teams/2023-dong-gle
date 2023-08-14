package org.donggle.backend.application.service.vendor.tistory.dto.response;

public record TistoryItemResponse<T>(
        int status,
        T item
) {
}