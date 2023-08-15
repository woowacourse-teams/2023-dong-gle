package org.donggle.backend.ui;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.request.DeleteWritingsRequest;
import org.donggle.backend.application.service.request.RestoreWritingsRequest;
import org.donggle.backend.auth.support.AuthenticationPrincipal;
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
    @GetMapping
    public ResponseEntity<Void> trashGetWritings(@AuthenticationPrincipal final Long memberId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> trashDeleteWritings(@AuthenticationPrincipal final Long memberId,
                                                    @RequestBody final DeleteWritingsRequest request) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restore")
    public ResponseEntity<Void> trashRestoreWritings(@AuthenticationPrincipal final Long memberId,
                                                     @RequestBody final RestoreWritingsRequest request) {
        return ResponseEntity.ok().build();
    }
}
