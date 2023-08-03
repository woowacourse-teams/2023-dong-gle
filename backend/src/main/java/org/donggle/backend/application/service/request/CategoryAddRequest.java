package org.donggle.backend.application.service.request;

import jakarta.validation.constraints.NotNull;

public record CategoryAddRequest(
        @NotNull(message = "카테고리 이름을 입력해주세요.")
        String categoryName
) {
}
