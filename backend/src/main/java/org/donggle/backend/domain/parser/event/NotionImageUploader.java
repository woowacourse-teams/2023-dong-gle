package org.donggle.backend.domain.parser.event;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Component
public class NotionImageUploader implements ImageUploader {
    public static final int MAX_IMAGE_SIZE = 5 * 1024 * 1024;
    private final S3Uploader s3Uploader;
    private final String s3Url;

    public NotionImageUploader(@Value("${aws.s3.url}") final String s3Url,
                               final S3Uploader s3Uploader) {
        this.s3Url = s3Url;
        this.s3Uploader = s3Uploader;
    }

    @Override
    public Optional<String> syncUpload(final String url) {
        final String fileName = Paths.get(URI.create(url).getPath()).getFileName().toString();
        final MediaType mediaType = parseMediaTypeFromFileName(fileName);
        final String uniqueFileName = generateUniqueFileName(fileName);

        try {
            final URLConnection urlConnection = new URL(url).openConnection();
            if (urlConnection.getContentLengthLong() > MAX_IMAGE_SIZE) {
                return Optional.empty();
            }
            try (final InputStream inputStream = urlConnection.getInputStream()) {
                final byte[] data = inputStream.readAllBytes();
                s3Uploader.upload(uniqueFileName, mediaType, data);
                return Optional.of(s3Url + uniqueFileName);
            }
        } catch (final Exception e) {
            return Optional.empty();
        }
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
