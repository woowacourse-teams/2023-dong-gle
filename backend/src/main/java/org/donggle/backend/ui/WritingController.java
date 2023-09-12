package org.donggle.backend.ui;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.PublishService;
import org.donggle.backend.application.service.WritingService;
import org.donggle.backend.application.service.request.MarkdownUploadRequest;
import org.donggle.backend.application.service.request.NotionUploadRequest;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.application.service.request.WritingModifyRequest;
import org.donggle.backend.ui.common.AuthenticationPrincipal;
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
@RequiredArgsConstructor
@RequestMapping("/writings")
public class WritingController {
    private final WritingService writingService;
    private final PublishService publishService;

    @PostMapping(value = "/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> writingAdd(
            @AuthenticationPrincipal final Long memberId,
            @Valid final MarkdownUploadRequest request
    ) throws IOException {
        final Long writingId = writingService.uploadMarkDownFile(memberId, request);
        return ResponseEntity.created(URI.create("/writings/" + writingId)).build();
    }

    @PostMapping("/notion")
    public ResponseEntity<Void> writingAdd(
            @AuthenticationPrincipal final Long memberId,
            @Valid @RequestBody final NotionUploadRequest request
    ) {
        final Long writingId = writingService.uploadNotionPage(memberId, request);
        return ResponseEntity.created(URI.create("/writings/" + writingId)).build();
    }

    @GetMapping("/{writingId}")
    public ResponseEntity<WritingResponse> writingDetails(
            @AuthenticationPrincipal final Long memberId,
            @PathVariable final Long writingId
    ) {
        final WritingResponse response = writingService.findWriting(memberId, writingId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{writingId}")
    public ResponseEntity<Void> writingModify(
            @AuthenticationPrincipal final Long memberId,
            @PathVariable final Long writingId,
            @RequestBody final WritingModifyRequest request
    ) {
        if (request.title() != null) {
            writingService.modifyWritingTitle(memberId, writingId, request);
        } else if (request.nextWritingId() != null && request.targetCategoryId() != null) {
            writingService.modifyWritingOrder(memberId, writingId, request);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{writingId}/properties")
    public ResponseEntity<WritingPropertiesResponse> writingPropertiesDetails(
            @AuthenticationPrincipal final Long memberId,
            @PathVariable final Long writingId
    ) {
        final WritingPropertiesResponse response = writingService.findWritingProperties(memberId, writingId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<WritingListWithCategoryResponse> writingListWithCategory(
            @AuthenticationPrincipal final Long memberId,
            @RequestParam final Long categoryId
    ) {
        final WritingListWithCategoryResponse response = writingService.findWritingListByCategoryId(memberId, categoryId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{writingId}/publish")
    public ResponseEntity<Void> writingPublish(
            @AuthenticationPrincipal final Long memberId,
            @PathVariable final Long writingId,
            @Valid @RequestBody final PublishRequest request
    ) {
        publishService.publishWriting(memberId, writingId, request);
        return ResponseEntity.ok().build();
    }
}
