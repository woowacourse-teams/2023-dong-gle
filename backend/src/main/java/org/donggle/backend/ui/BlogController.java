package org.donggle.backend.ui;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.infrastructure.client.tistory.TistoryApiClient;
import org.donggle.backend.ui.common.AuthenticationPrincipal;
import org.donggle.backend.ui.response.TistoryCategoryListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blogs")
public class BlogController {
    private final TistoryApiClient tistoryApiService;

    @GetMapping("/tistory/category")
    public ResponseEntity<TistoryCategoryListResponse> tistoryCategoryList(
            @AuthenticationPrincipal final Long memberId
    ) {
        final TistoryCategoryListResponse response = tistoryApiService.findCategory(memberId);
        return ResponseEntity.ok(response);
    }
}
