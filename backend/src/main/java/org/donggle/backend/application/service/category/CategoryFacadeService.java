package org.donggle.backend.application.service.category;

import org.donggle.backend.application.service.request.CategoryAddRequest;
import org.donggle.backend.infrastructure.concurrent.LockRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryFacadeService {
    private final CategoryService categoryService;
    private final LockRepository lockRepository;

    public CategoryFacadeService(final CategoryService categoryService, final LockRepository lockRepository) {
        this.categoryService = categoryService;
        this.lockRepository = lockRepository;
    }

    public Long addCategory(final Long memberId, final CategoryAddRequest request) {
        return lockRepository.executeWithLock(
                memberId.toString(),
                () -> categoryService.addCategory(memberId, request));
    }
}
