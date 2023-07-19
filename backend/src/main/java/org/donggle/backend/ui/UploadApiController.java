package org.donggle.backend.ui;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.UploadWritingService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/writings")
@RequiredArgsConstructor
public class UploadApiController {
    private static final String MD_FORMAT = ".md";

    private final UploadWritingService uploadWritingService;

    @PostMapping(value = "/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> upload(final MultipartFile file) {
        final String originalFilename = file.getOriginalFilename();
        validateFileFormat(originalFilename);

        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            final String content = reader.lines().collect(Collectors.joining("\n"));
            final Long writingId = uploadWritingService.uploadMarkDownFile(1L, originalFilename, content);
            return ResponseEntity.created(URI.create("/writings/" + writingId)).build();
        } catch (final IOException e) {
            // TODO : 예외 처리 로직 추가
            return ResponseEntity.internalServerError().build();
        }
    }

    private void validateFileFormat(final String originalFilename) {
        if (!Objects.requireNonNull(originalFilename).endsWith(MD_FORMAT)) {
            throw new UnsupportedOperationException();
        }
    }
}
