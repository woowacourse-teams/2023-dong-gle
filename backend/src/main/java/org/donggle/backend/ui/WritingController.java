package org.donggle.backend.ui;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.PublishService;
import org.donggle.backend.application.service.WritingService;
import org.donggle.backend.application.service.request.NotionUploadRequest;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.application.service.request.WritingTitleRequest;
import org.donggle.backend.ui.response.WritingPropertiesResponse;
import org.donggle.backend.ui.response.WritingResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/writings")
@RequiredArgsConstructor
public class WritingController {
    private final WritingService writingService;
    private final PublishService publishService;

    @PostMapping(value = "/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> writingAdd(final MultipartFile file) throws IOException {
        final Long writingId = writingService.uploadMarkDownFile(1L, file);
        return ResponseEntity.created(URI.create("/writings/" + writingId)).build();
    }

    @PostMapping("/notion")
    public ResponseEntity<Void> writingAdd(@RequestBody final NotionUploadRequest request) {
        final Long writingId = writingService.uploadNotionPage(1L, request);
        return ResponseEntity.created(URI.create("/writings/" + writingId)).build();
    }

    @GetMapping("/{writingId}")
    public ResponseEntity<WritingResponse> writingDetails(@PathVariable final Long writingId) {
        final WritingResponse response = writingService.findWriting(1L, writingId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{writingId}")
    public ResponseEntity<Void> writingTitleModify(@PathVariable final Long writingId,
                                                   @RequestBody final WritingTitleRequest request) {
        writingService.modifyWritingTitle(1L, writingId, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{writingId}/properties")
    public ResponseEntity<WritingPropertiesResponse> writingPropertiesDetails(@PathVariable final Long writingId) {
        final WritingPropertiesResponse response = writingService.findWritingProperties(1L, writingId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{writingId}/publish")
    public ResponseEntity<Void> writingPublish(@PathVariable final Long writingId, @RequestBody final PublishRequest request) {
        publishService.publishWriting(1L, writingId, request);
        return ResponseEntity.ok().build();
    }


}
