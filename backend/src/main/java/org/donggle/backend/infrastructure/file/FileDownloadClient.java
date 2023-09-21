package org.donggle.backend.infrastructure.file;

import java.util.Optional;

public interface FileDownloadClient {
    Optional<byte[]> download(String url);
}
