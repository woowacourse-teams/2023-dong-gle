package org.donggle.backend.infrastructure.client.tistory.dto.response;

public record TistoryItemResponse<T>(
        int status,
        T item
) {
}