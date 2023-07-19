package org.donggle.backend.ui;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.WritingService;
import org.donggle.backend.dto.WritingResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class WritingController {
    private static final String MD_FORMAT = ".md";

    private final WritingService writingService;

    @PostMapping(value = "/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> writingAddByFile(final MultipartFile file) {
        final String originalFilename = file.getOriginalFilename();
        validateFileFormat(originalFilename);

        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            final String content = reader.lines().collect(Collectors.joining("\n"));
            final int endIndex = originalFilename.lastIndexOf(MD_FORMAT);
            final String title = originalFilename.substring(0, endIndex);
            final Long writingId = writingService.uploadMarkDownFile(1L, title, content);
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

    @GetMapping("/{writingId}")
    public ResponseEntity<WritingResponse> writingDetails(@PathVariable final Long writingId) {
        final WritingResponse response = writingService.findWriting(1L, writingId);
        return ResponseEntity.ok(response);
    }
}
