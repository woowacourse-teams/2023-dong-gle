package org.donggle.backend.ui;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.blog.PublishFacadeService;
import org.donggle.backend.application.service.request.MarkdownUploadRequest;
import org.donggle.backend.application.service.request.NotionUploadRequest;
import org.donggle.backend.application.service.request.WritingModifyRequest;
import org.donggle.backend.application.service.writing.WritingFacadeService;
import org.donggle.backend.ui.common.AuthenticationPrincipal;
import org.donggle.backend.ui.response.WritingHomeResponse;
import org.donggle.backend.ui.response.WritingListWithCategoryResponse;
import org.donggle.backend.ui.response.WritingPropertiesResponse;
import org.donggle.backend.ui.response.WritingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final WritingFacadeService writingFacadeService;
    private final PublishFacadeService blogService;

    @PostMapping(value = "/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> writingAdd(
            @AuthenticationPrincipal final Long memberId,
            @Valid final MarkdownUploadRequest request
    ) throws IOException {
        final Long writingId = writingFacadeService.uploadMarkDownFile(memberId, request);
        return ResponseEntity.created(URI.create("/writings/" + writingId)).build();
    }

    @PostMapping("/notion")
    public ResponseEntity<Void> writingAdd(
            @AuthenticationPrincipal final Long memberId,
            @Valid @RequestBody final NotionUploadRequest request
    ) {
        final Long writingId = writingFacadeService.uploadNotionPage(memberId, request);
        return ResponseEntity.created(URI.create("/writings/" + writingId)).build();
    }

    @GetMapping("/home")
    public ResponseEntity<Page<WritingHomeResponse>> showHomePage(
            @AuthenticationPrincipal final Long memberId,
            final Pageable pageable) {
        final Page<WritingHomeResponse> response = writingFacadeService.findAll(memberId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{writingId}")
    public ResponseEntity<WritingResponse> writingDetails(
            @AuthenticationPrincipal final Long memberId,
            @PathVariable final Long writingId
    ) {
        final WritingResponse response = writingFacadeService.findWriting(memberId, writingId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{writingId}")
    public ResponseEntity<Void> writingModify(
            @AuthenticationPrincipal final Long memberId,
            @PathVariable final Long writingId,
            @RequestBody final WritingModifyRequest request
    ) {
        if (request.title() != null) {
            writingFacadeService.modifyWritingTitle(memberId, writingId, request);
        } else if (request.nextWritingId() != null && request.targetCategoryId() != null) {
            writingFacadeService.modifyWritingOrder(memberId, writingId, request);
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
        final WritingPropertiesResponse response = writingFacadeService.findWritingProperties(memberId, writingId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<WritingListWithCategoryResponse> writingListWithCategory(
            @AuthenticationPrincipal final Long memberId,
            @RequestParam final Long categoryId
    ) {
        final WritingListWithCategoryResponse response = writingFacadeService.findWritingListByCategoryId(memberId, categoryId);
        return ResponseEntity.ok(response);
    }
}
