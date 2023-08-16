package org.donggle.backend.ui;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.CategoryService;
import org.donggle.backend.application.service.request.CategoryAddRequest;
import org.donggle.backend.application.service.request.CategoryModifyRequest;
import org.donggle.backend.auth.support.AuthenticationPrincipal;
import org.donggle.backend.ui.response.CategoryListResponse;
import org.donggle.backend.ui.response.CategoryWritingsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Void> categoryAdd(
            @AuthenticationPrincipal final Long memberId,
            @Valid @RequestBody final CategoryAddRequest request
    ) {
        final Long categoryId = categoryService.addCategory(memberId, request);
        return ResponseEntity.created(URI.create("/categories/" + categoryId)).build();
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<Void> categoryModify(
            @AuthenticationPrincipal final Long memberId,
            @PathVariable final Long categoryId,
            @RequestBody final CategoryModifyRequest request
    ) {
        if (request.categoryName() != null) {
            categoryService.modifyCategoryName(memberId, categoryId, request);
        } else if (request.nextCategoryId() != null) {
            categoryService.modifyCategoryOrder(memberId, categoryId, request);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> categoryRemove(
            @AuthenticationPrincipal final Long memberId,
            @PathVariable final Long categoryId
    ) {
        categoryService.removeCategory(memberId, categoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<CategoryListResponse> categoryList(@AuthenticationPrincipal final Long memberId) {
        final CategoryListResponse response = categoryService.findAll(memberId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryWritingsResponse> categoryWritingList(
            @AuthenticationPrincipal final Long memberId,
            @PathVariable final Long categoryId
    ) {
        final CategoryWritingsResponse response = categoryService.findAllWritings(memberId, categoryId);
        return ResponseEntity.ok(response);
    }
}
