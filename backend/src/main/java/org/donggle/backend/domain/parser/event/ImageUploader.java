package org.donggle.backend.domain.parser.event;

import java.util.Optional;

public interface ImageUploader {
    Optional<String> syncUpload(String url);

}
