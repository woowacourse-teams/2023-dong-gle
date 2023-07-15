package org.donggle.backend.ui;

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

@RestController
@RequestMapping("/writings")
public class UploadApiController {
    @PostMapping(value = "/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> upload(final MultipartFile file) {
        final String originalFilename = file.getOriginalFilename();
        System.out.println("file.getOriginalFilename() = " + originalFilename);
        if (!Objects.requireNonNull(originalFilename).endsWith(".md")) {
            System.out.println("지원하지 않는 형식입니다.");
            throw new UnsupportedOperationException();
        }
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            final StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            System.out.println("builder = " + builder);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.created(URI.create("/writings/" + 1)).build();
    }
}
