package org.donggle.backend.ui;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.CategoryService;
import org.donggle.backend.application.service.request.CategoryAddRequest;
import org.donggle.backend.application.service.request.CategoryModifyRequest;
import org.springframework.http.ResponseEntity;
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
        categoryService.modifyCategory(1L, categoryId, request);
        return ResponseEntity.noContent().build();
    }
}
