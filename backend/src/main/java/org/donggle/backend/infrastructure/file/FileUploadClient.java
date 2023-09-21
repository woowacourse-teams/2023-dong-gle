package org.donggle.backend.infrastructure.file;

import org.springframework.http.MediaType;

import java.util.Optional;

public interface FileUploadClient {
    Optional<String> upload(String fileName, MediaType mediaType, byte[] data);
}
