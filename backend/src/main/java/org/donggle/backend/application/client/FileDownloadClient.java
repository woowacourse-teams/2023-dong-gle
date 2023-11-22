package org.donggle.backend.application.client;

import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;

import java.util.Optional;

public interface FileDownloadClient {
    Optional<byte[]> download(String url);

    Flux<DataBuffer> downloadAsFlux(String url);
}
