package org.donggle.backend.application.service.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record MarkdownUploadRequest(
        MultipartFile file,
        @NotNull(message = "카테고리 아이디는 필수입니다.")
        Long categoryId
) {
}
