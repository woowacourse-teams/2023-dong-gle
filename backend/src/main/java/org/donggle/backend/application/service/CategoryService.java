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
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.exception.business.InvalidBasicCategoryException;
import org.donggle.backend.exception.notfound.CategoryNotFoundException;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.donggle.backend.ui.response.CategoryListResponse;
import org.donggle.backend.ui.response.CategoryResponse;
import org.donggle.backend.ui.response.CategoryWritingsResponse;
import org.donggle.backend.ui.response.WritingSimpleResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final WritingRepository writingRepository;

    public Long addCategory(final Long memberId, final CategoryAddRequest request) {
        final Member findMember = findMember(memberId);
        final Category category = Category.of(
                new CategoryName(request.categoryName()),
                findMember
        );
        final Category lastCategory = categoryRepository.findLastCategoryByMemberId(memberId)
                .orElseThrow(IllegalStateException::new);
        final Category savedCategory = categoryRepository.save(category);
        lastCategory.changeNextCategory(savedCategory);
        return savedCategory.getId();
    }

    @Transactional(readOnly = true)
    public CategoryListResponse findAll(final Long memberId) {
        //TODO: member checking
        final List<Category> categories = categoryRepository.findAllByMemberId(memberId);
        final List<CategoryResponse> categoryResponses = categories.stream()
                .map(category -> new CategoryResponse(category.getId(), category.getCategoryNameValue()))
                .toList();
        return new CategoryListResponse(categoryResponses);
    }

    @Transactional(readOnly = true)
    public CategoryWritingsResponse findAllWritings(final Long memberId, final Long categoryId) {
        //TODO: member checking
        final Category findCategory = findCategory(categoryId);
        final List<Writing> findWritings = writingRepository.findAllByCategoryId(findCategory.getId());
        List<Writing> sortedWriting = sortedWriting(findWritings);

        final List<WritingSimpleResponse> writingSimpleResponses = sortedWriting.stream()
                .map(writing -> new WritingSimpleResponse(writing.getId(), writing.getTitleValue()))
                .toList();
        return new CategoryWritingsResponse(findCategory.getId(), findCategory.getCategoryNameValue(), writingSimpleResponses);
    }

    private List<Writing> sortedWriting(final List<Writing> findWritings) {
        List<Writing> copy = new ArrayList<>(findWritings);
        final List<Writing> nextWritings = findWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        final Map<Writing, Writing> aoq = new LinkedHashMap<>();
        for (final Writing findWriting : findWritings) {
            aoq.put(findWriting, findWriting.getNextWriting());
        }
        copy.removeAll(nextWritings);
        Writing firstWriting = copy.get(0); // 처음꺼 찾음

        final List<Writing> sortedWriting = new ArrayList<>();
        sortedWriting.add(firstWriting);
        while (firstWriting.getNextWriting() != null) {
            firstWriting = aoq.get(firstWriting);
            sortedWriting.add(firstWriting);
        }
        return sortedWriting;
    }

    public void modifyCategory(final Long memberId, final Long categoryId, final CategoryModifyRequest request) {
        //TODO: member checking
        final Category findCategory = findCategory(categoryId);
        validateBasicCategory(findCategory);
        findCategory.changeName(new CategoryName(request.categoryName()));
    }

    public void removeCategory(final Long memberId, final Long categoryId) {
        //TODO: member checking
        final Category findCategory = findCategory(categoryId);
        validateBasicCategory(findCategory);

        final Category basicCategory = findBasicCategoryByMemberId(memberId);
        writingRepository.findAllByCategoryId(findCategory.getId())
                .forEach(writing -> writing.changeCategory(basicCategory));

        final Category nextCategory = findCategory.getNextCategory();
        final Category preCategory = findPreCategoryByCategoryId(findCategory.getId());
        findCategory.changeNextCategory(null);
        categoryRepository.flush();
        preCategory.changeNextCategory(nextCategory);
        categoryRepository.delete(findCategory);

        //TODO: 글 순서 추가 후 글 순서 변경 로직 추가
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

    private Member findMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private Category findCategory(final Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }
}
