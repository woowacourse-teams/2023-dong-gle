package org.donggle.backend.application.service.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PublishRequest(
        @NotNull(message = "블로그를 선택해주세요.")
        String publishTo,
        List<String> tags
) {
}
