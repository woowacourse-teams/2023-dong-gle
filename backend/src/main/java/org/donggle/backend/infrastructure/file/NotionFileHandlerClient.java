package org.donggle.backend.infrastructure.file;

import org.donggle.backend.application.client.FileDownloadClient;
import org.donggle.backend.application.client.FileHandlerClient;
import org.donggle.backend.application.client.FileUploadClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Component
public class NotionFileHandlerClient implements FileHandlerClient {
    private final FileUploadClient fileUploadClient;
    private final FileDownloadClient fileDownloadClient;

    public NotionFileHandlerClient(final FileUploadClient fileUploadClient, final FileDownloadClient fileDownloadClient) {
        this.fileUploadClient = fileUploadClient;
        this.fileDownloadClient = fileDownloadClient;
    }

    @Override
    public Optional<String> syncUpload(final String url) {
        final String fileName = Paths.get(URI.create(url).getPath()).getFileName().toString();
        final MediaType mediaType = parseMediaTypeFromFileName(fileName);
        final String uniqueFileName = generateUniqueFileName(fileName);
        return fileDownloadClient.download(url)
                .flatMap(data -> fileUploadClient.upload(uniqueFileName, mediaType, data));
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
