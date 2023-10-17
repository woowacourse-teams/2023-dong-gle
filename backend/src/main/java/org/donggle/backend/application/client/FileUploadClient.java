package org.donggle.backend.application.client;

import org.springframework.http.MediaType;

import java.util.Optional;

public interface FileUploadClient {
    Optional<String> upload(String fileName, MediaType mediaType, byte[] data);
}
