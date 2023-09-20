package org.donggle.backend.domain.parser.event;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
public class S3Uploader {
    private final S3Client s3Client;
    private final String bucketName;
    private final String key;


    public S3Uploader(final S3Client s3Client,
                      @Value("${aws.s3.bucket}") final String bucketName,
                      @Value("${aws.s3.key}") final String key) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.key = key;
    }

    public void upload(final String fileName, final MediaType mediaType, final byte[] data) {
        final PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key + fileName)
                .contentType(mediaType.toString())
                .build();
        final RequestBody requestBody = RequestBody.fromBytes(data);
        s3Client.putObject(request, requestBody);
    }
}
