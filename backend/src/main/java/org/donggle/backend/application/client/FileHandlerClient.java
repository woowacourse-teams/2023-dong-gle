package org.donggle.backend.application.client;

import java.util.Optional;

public interface FileHandlerClient {
    Optional<String> syncUpload(String url);

}
