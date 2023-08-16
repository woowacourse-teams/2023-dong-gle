package org.donggle.backend.ui;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.TrashService;
import org.donggle.backend.application.service.request.DeleteWritingsRequest;
import org.donggle.backend.application.service.request.RestoreWritingsRequest;
import org.donggle.backend.auth.support.AuthenticationPrincipal;
import org.donggle.backend.ui.response.TrashResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trash")
public class TrashController {
    private final TrashService trashService;

    @GetMapping
    public ResponseEntity<TrashResponse> trashGetWritings(@AuthenticationPrincipal final Long memberId) {
        final TrashResponse trashResponse = trashService.findTrashedWritingList(memberId);
        return ResponseEntity.ok(trashResponse);
    }

    @PostMapping
    public ResponseEntity<Void> trashDeleteWritings(@AuthenticationPrincipal final Long memberId,
                                                    @RequestBody final DeleteWritingsRequest request) {
        if (request.isPermanentDelete()) {
            trashService.deleteWritings(memberId, request.writingIds());
        } else {
            trashService.trashWritings(memberId, request.writingIds());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restore")
    public ResponseEntity<Void> trashRestoreWritings(@AuthenticationPrincipal final Long memberId,
                                                     @RequestBody final RestoreWritingsRequest request) {
        trashService.restoreWritings(memberId, request.writingIds());
        return ResponseEntity.ok().build();
    }
}
