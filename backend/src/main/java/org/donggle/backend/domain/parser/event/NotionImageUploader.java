package org.donggle.backend.domain.parser.event;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Component
public class NotionImageUploader {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final String s3Url;

    public NotionImageUploader(final ApplicationEventPublisher applicationEventPublisher,
                               @Value("${aws.s3.url}") final String s3Url) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.s3Url = s3Url;
    }

    public String upload(final String url) {
        final String fileName = Paths.get(URI.create(url).getPath()).getFileName().toString();
        final MediaType mediaType = parseMediaTypeFromFileName(fileName);
        final String uniqueFileName = generateUniqueFileName(fileName);

        applicationEventPublisher.publishEvent(new NotionImageUploadEvent(url, uniqueFileName, mediaType));
        return s3Url + uniqueFileName;
    }

    private String generateUniqueFileName(final String fileName) {
        final String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf("."));
        final String extension = fileName.substring(fileName.lastIndexOf("."));
        final String uniqueName = UUID.randomUUID().toString();
        return fileNameWithoutExtension + "_" + uniqueName + extension;
    }

    private MediaType parseMediaTypeFromFileName(final String fileName) {
        return Optional.ofNullable(URLConnection.guessContentTypeFromName(fileName))
                .map(MediaType::parseMediaType)
                .orElseGet(() -> MediaType.APPLICATION_OCTET_STREAM);
    }
}
