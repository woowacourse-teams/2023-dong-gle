package org.donggle.backend.ui;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.PublishService;
import org.donggle.backend.application.service.WritingService;
import org.donggle.backend.application.service.request.MarkdownUploadRequest;
import org.donggle.backend.application.service.request.NotionUploadRequest;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.application.service.request.WritingModifyRequest;
import org.donggle.backend.ui.response.WritingListWithCategoryResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/writings")
@RequiredArgsConstructor
public class WritingController {
    private final WritingService writingService;
    private final PublishService publishService;

    @PostMapping(value = "/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> writingAdd(final MarkdownUploadRequest request) throws IOException {
        final Long writingId = writingService.uploadMarkDownFile(1L, request);
        return ResponseEntity.created(URI.create("/writings/" + writingId)).build();
    }

    @PostMapping("/notion")
    public ResponseEntity<Void> writingAdd(@Valid @RequestBody final NotionUploadRequest request) {
        final Long writingId = writingService.uploadNotionPage(1L, request);
        return ResponseEntity.created(URI.create("/writings/" + writingId)).build();
    }

    @GetMapping("/{writingId}")
    public ResponseEntity<WritingResponse> writingDetails(@PathVariable final Long writingId) {
        final WritingResponse response = writingService.findWriting(1L, writingId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{writingId}")
    public ResponseEntity<Void> writingModify(@PathVariable final Long writingId,
                                              @RequestBody final WritingModifyRequest request) {
        if (request.title() != null) {
            writingService.modifyWritingTitle(1L, writingId, request);
        } else if (request.nextWritingId() != null && request.targetCategoryId() != null) {
            writingService.modifyWritingOrder(1L, writingId, request);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{writingId}/properties")
    public ResponseEntity<WritingPropertiesResponse> writingPropertiesDetails(@PathVariable final Long writingId) {
        final WritingPropertiesResponse response = writingService.findWritingProperties(1L, writingId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<WritingListWithCategoryResponse> writingListWithCategory(@RequestParam final Long categoryId) {
        final WritingListWithCategoryResponse response = writingService.findWritingListByCategoryId(1L, categoryId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{writingId}/publish")
    public ResponseEntity<Void> writingPublish(@PathVariable final Long writingId,
                                               @Valid @RequestBody final PublishRequest request) {
        publishService.publishWriting(1L, writingId, request);
        return ResponseEntity.ok().build();
    }
}
