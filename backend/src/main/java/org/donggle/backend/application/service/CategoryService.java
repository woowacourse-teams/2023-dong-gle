package org.donggle.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.service.request.CategoryAddRequest;
import org.donggle.backend.application.service.request.CategoryModifyRequest;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.category.CategoryName;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.exception.business.InvalidBasicCategoryException;
import org.donggle.backend.exception.notfound.CategoryNotFoundException;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.donggle.backend.ui.response.CategoriesResponse;
import org.donggle.backend.ui.response.CategoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long addCategory(final Long memberId, final CategoryAddRequest request) {
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        final Category category = Category.of(
                new CategoryName(request.categoryName()),
                null,
                findMember
        );
        final Category lastCategory = categoryRepository.findLastByMemberId(memberId)
                .orElseThrow(IllegalStateException::new);
        final Category savedCategory = categoryRepository.save(category);
        lastCategory.updateNext(savedCategory);
        return savedCategory.getId();
    }

    @Transactional
    public void modifyCategory(final Long memberId, final Long categoryId, final CategoryModifyRequest request) {
        //TODO: member checking
        final Category findCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        validateBasicCategory(findCategory);
        findCategory.changeName(new CategoryName(request.categoryName()));
    }

    @Transactional
    public void removeCategory(final Long memberId, final Long categoryId) {
        //TODO: member checking
        final Category findCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        validateBasicCategory(findCategory);
        //TODO: 카테고리 삭제 시 카테고리에 포함된 글들 기본 카테고리로 이동
        categoryRepository.deleteById(categoryId);
    }

    private void validateBasicCategory(final Category category) {
        if (category.isBasic()) {
            throw new InvalidBasicCategoryException();
        }
    }

    public CategoriesResponse findAll(final Long memberId) {
        //TODO: member checking
        final List<Category> categories = categoryRepository.findAllByMemberId(memberId);
        final List<CategoryResponse> categoryResponses = categories.stream()
                .map(category -> new CategoryResponse(category.getId(), category.getCategoryNameValue()))
                .toList();
        return new CategoriesResponse(categoryResponses);
    }
}
