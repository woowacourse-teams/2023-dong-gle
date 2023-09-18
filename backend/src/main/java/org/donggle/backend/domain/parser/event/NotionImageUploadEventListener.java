package org.donggle.backend.domain.parser.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Component
@Slf4j
public class NotionImageUploadEventListener {
    private final S3Client s3Client;
    private final String bucketName;
    private final String key;


    public NotionImageUploadEventListener(final S3Client s3Client,
                                          @Value("${aws.s3.bucket}") final String bucketName,
                                          @Value("${aws.s3.key}") final String key) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.key = key;
    }

    @Async
    @EventListener
    public void handle(final NotionImageUploadEvent event) throws IOException {
        final URLConnection urlConnection = new URL(event.sourceUrl()).openConnection();
        final InputStream inputStream = urlConnection.getInputStream();

        final PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key + event.key())
                .contentType(event.mediaType().toString())
                .build();

        final RequestBody imageRequestBody = RequestBody.fromInputStream(inputStream, urlConnection.getContentLengthLong());
        s3Client.putObject(request, imageRequestBody);
    }
}
