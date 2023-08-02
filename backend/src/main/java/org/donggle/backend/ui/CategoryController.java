package org.donggle.backend.ui;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.CategoryService;
import org.donggle.backend.application.service.request.CategoryAddRequest;
import org.donggle.backend.application.service.request.CategoryModifyRequest;
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
    public ResponseEntity<Void> categoryAdd(@RequestBody final CategoryAddRequest request) {
        final Long categoryId = categoryService.addCategory(1L, request);
        return ResponseEntity.created(URI.create("/categories/" + categoryId)).build();
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<Void> categoryModify(@PathVariable final Long categoryId,
                                               @RequestBody final CategoryModifyRequest request) {
        categoryService.modifyCategoryName(1L, categoryId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> categoryRemove(@PathVariable final Long categoryId) {
        categoryService.removeCategory(1L, categoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<CategoryListResponse> categoryList() {
        final CategoryListResponse response = categoryService.findAll(1L);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryWritingsResponse> categoryWritingList(@PathVariable final Long categoryId) {
        final CategoryWritingsResponse response = categoryService.findAllWritings(1L, categoryId);
        return ResponseEntity.ok(response);
    }
}
