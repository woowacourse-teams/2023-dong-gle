package org.donggle.backend.infrastructure.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.Optional;

@Component
@Slf4j
public class S3UploadClient implements FileUploadClient {
    private final S3Client s3Client;
    private final String bucketName;
    private final String key;
    private final String serverImageUrl;


    public S3UploadClient(final S3Client s3Client,
                          @Value("${aws.s3.bucket}") final String bucketName,
                          @Value("${aws.s3.key}") final String key,
                          @Value("${uploader.image-url}") final String serverImageUrl) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.key = key;
        this.serverImageUrl = serverImageUrl;
    }

    @Override
    public Optional<String> upload(final String fileName, final MediaType mediaType, final byte[] data) {
        try {
            final PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key + fileName)
                    .contentType(mediaType.toString())
                    .build();
            final RequestBody requestBody = RequestBody.fromBytes(data);
            s3Client.putObject(request, requestBody);
            return Optional.of(getUploadedUrl(fileName));
        } catch (final Exception e) {
            return Optional.empty();
        }
    }

    private String getUploadedUrl(final String fileName) {
        return serverImageUrl + fileName;
    }
}
