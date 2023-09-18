package org.donggle.backend.ui;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.blog.PublishFacadeService;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.ui.common.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/writings/{writingId}/publish")
public class PublishController {
    private final PublishFacadeService blogService;

    @PostMapping("/tistory")
    public ResponseEntity<Void> publishToTistory(
            @AuthenticationPrincipal final Long memberId,
            @PathVariable final Long writingId,
            @Valid @RequestBody final PublishRequest request
    ) {
        blogService.publishWriting(memberId, writingId, BlogType.TISTORY, PublishRequest.tistory(request));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/medium")
    public ResponseEntity<Void> publishToMedium(
            @AuthenticationPrincipal final Long memberId,
            @PathVariable final Long writingId,
            @Valid @RequestBody final PublishRequest request
    ) {
        blogService.publishWriting(memberId, writingId, BlogType.MEDIUM, PublishRequest.medium(request));
        return ResponseEntity.ok().build();
    }
}
