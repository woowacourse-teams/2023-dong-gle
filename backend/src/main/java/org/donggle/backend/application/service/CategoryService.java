package org.donggle.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
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
    private final WritingRepository writingRepository;

    @Transactional
    public Long addCategory(final Long memberId, final CategoryAddRequest request) {
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        final Category category = Category.of(
                new CategoryName(request.categoryName()),
                null,
                findMember
        );
        final Category lastCategory = categoryRepository.findLastCategoryByMemberId(memberId)
                .orElseThrow(IllegalStateException::new);
        final Category savedCategory = categoryRepository.save(category);
        lastCategory.changeNextCategory(savedCategory);
        return savedCategory.getId();
    }

    public CategoriesResponse findAll(final Long memberId) {
        //TODO: member checking
        final List<Category> categories = categoryRepository.findAllByMemberId(memberId);
        final List<CategoryResponse> categoryResponses = categories.stream()
                .map(category -> new CategoryResponse(category.getId(), category.getCategoryNameValue()))
                .toList();
        return new CategoriesResponse(categoryResponses);
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

        final Category basicCategory = findBasicCategoryByMemberId(memberId);
        writingRepository.findAllByCategoryId(categoryId)
                .forEach(writing -> writing.changeCategory(basicCategory));

        final Category preCategory = findPreCategoryByCategoryId(categoryId);
        preCategory.changeNextCategory(findCategory.getNextCategory());

        //TODO: 글 순서 추가 후 글 순서 변경 로직 추가

        categoryRepository.deleteById(categoryId);
    }

    private void validateBasicCategory(final Category category) {
        if (category.isBasic()) {
            throw new InvalidBasicCategoryException();
        }
    }

    private Category findBasicCategoryByMemberId(final Long memberId) {
        return categoryRepository.findFirstByMemberId(memberId)
                .orElseThrow(IllegalStateException::new);
    }

    private Category findPreCategoryByCategoryId(final Long categoryId) {
        return categoryRepository.findPreCategoryByCategoryId(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }
}
