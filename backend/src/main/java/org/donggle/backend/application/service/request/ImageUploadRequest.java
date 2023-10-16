package org.donggle.backend.application.service.request;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;


public record ImageUploadRequest(
        Flux<DataBuffer> imageDataFlux,
        MediaType mediaType
) {
}
