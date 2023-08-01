package org.donggle.backend.application.service.request;

import org.springframework.web.multipart.MultipartFile;

public record MarkdownUploadRequest(MultipartFile file, Long categoryId) {
}
