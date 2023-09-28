package org.donggle.backend.infrastructure.file;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

@Component
public class SyncFileDownloadClient implements FileDownloadClient {
    public static final int MAX_IMAGE_SIZE = 5 * 1024 * 1024;

    @Override
    public Optional<byte[]> download(final String url) {
        try {
            final URLConnection urlConnection = new URL(url).openConnection();
            if (urlConnection.getContentLengthLong() > MAX_IMAGE_SIZE) {
                return Optional.empty();
            }
            try (final InputStream inputStream = urlConnection.getInputStream()) {
                return Optional.ofNullable(inputStream.readAllBytes());
            }
        } catch (final Exception e) {
            return Optional.empty();
        }
    }
}
