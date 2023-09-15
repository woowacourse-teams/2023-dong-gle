package org.donggle.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.request.CategoryAddRequest;
import org.donggle.backend.application.service.request.CategoryModifyRequest;
import org.donggle.backend.domain.OrderStatus;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.category.CategoryName;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.exception.business.DuplicateCategoryNameException;
import org.donggle.backend.exception.business.EmptyCategoryNameException;
import org.donggle.backend.exception.business.InvalidBasicCategoryException;
import org.donggle.backend.exception.business.OverLengthCategoryNameException;
import org.donggle.backend.exception.notfound.CategoryNotFoundException;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.donggle.backend.ui.response.CategoryListResponse;
import org.donggle.backend.ui.response.CategoryResponse;
import org.donggle.backend.ui.response.CategoryWritingsResponse;
import org.donggle.backend.ui.response.WritingSimpleResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private static final int LAST_WRITING_FLAG = -1;
    private static final int FIRST_WRITING_INDEX = 0;

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final WritingRepository writingRepository;

    public Long addCategory(final Long memberId, final CategoryAddRequest request) {
        final Member findMember = findMember(memberId);
        final CategoryName categoryName = new CategoryName(request.categoryName());
        validateCategoryName(categoryName);
        final Category category = Category.of(categoryName, findMember);
        final Category lastCategory = findLastCategoryByMemberId(memberId);
        final Category savedCategory = categoryRepository.save(category);
        lastCategory.changeNextCategory(savedCategory);
        return savedCategory.getId();
    }

    private void validateCategoryName(final CategoryName categoryName) {
        if (categoryRepository.existsByCategoryName(categoryName)) {
            throw new DuplicateCategoryNameException(categoryName.getName());
        }
        if (categoryName.isBlank()) {
            throw new EmptyCategoryNameException();
        }
        if (categoryName.isOverLength()) {
            throw new OverLengthCategoryNameException();
        }
    }

    @Transactional(readOnly = true)
    public CategoryListResponse findAll(final Long memberId) {
        final Member findMember = findMember(memberId);
        final List<Category> categories = categoryRepository.findAllByMemberId(findMember.getId());
        final List<Category> sortedCategories = sortCategory(categories, findBasicCategoryByMemberId(findMember.getId()));
        final List<CategoryResponse> categoryResponses = sortedCategories.stream()
                .map(CategoryResponse::of)
                .toList();
        return CategoryListResponse.from(categoryResponses);
    }

    private List<Category> sortCategory(final List<Category> categories, final Category basicCategory) {
        final Map<Category, Category> categoryMap = new LinkedHashMap<>();
        for (final Category category : categories) {
            categoryMap.put(category, category.getNextCategory());
        }
        final List<Category> sortedCategories = new ArrayList<>();
        sortedCategories.add(basicCategory);
        Category targetCategory = basicCategory;
        while (Objects.nonNull(targetCategory.getNextCategory())) {
            targetCategory = categoryMap.get(targetCategory);
            sortedCategories.add(targetCategory);
        }
        return sortedCategories;
    }

    @Transactional(readOnly = true)
    public CategoryWritingsResponse findAllWritings(final Long memberId, final Long categoryId) {
        final Category findCategory = findCategory(memberId, categoryId);
        final List<Writing> findWritings = writingRepository.findAllByCategoryId(findCategory.getId());
        if (findWritings.isEmpty()) {
            return CategoryWritingsResponse.of(findCategory, Collections.emptyList());
        }
        final Long firstWritingId = findFirstWritingId(findWritings);
        final List<Long> sortWritingIds = sortWriting(findWritings, firstWritingId);
        final Map<Long, Writing> wiritingMap = findWritings.stream()
                .collect(Collectors.toMap(Writing::getId, writing -> writing));
        final List<Writing> sortWriting = sortWritingIds.stream()
                .map(wiritingMap::get)
                .toList();
        final List<WritingSimpleResponse> writingSimpleResponses = sortWriting.stream()
                .map(WritingSimpleResponse::from)
                .toList();
        return CategoryWritingsResponse.of(findCategory, writingSimpleResponses);
    }

    private List<Long> sortWriting(final List<Writing> writings, final Long firstWritingId) {
        final Map<Long, Long> writingMap = new LinkedHashMap<>();
        for (final Writing writing : writings) {
            writingMap.put(writing.getId(), writing.getNextId());
        }
        final List<Long> sortedWritingIds = new ArrayList<>();
        sortedWritingIds.add(firstWritingId);
        Long targetWritingId = firstWritingId;
        while (!Objects.equals(writingMap.get(targetWritingId), OrderStatus.END.getStatusValue())) {
            targetWritingId = writingMap.get(targetWritingId);
            sortedWritingIds.add(targetWritingId);
        }
        return sortedWritingIds;
    }

    private Long findFirstWritingId(final List<Writing> findWritings) {
        final List<Long> copy = new ArrayList<>(findWritings.stream()
                .map(Writing::getId)
                .toList());
        final List<Long> nextWritings = findWritings.stream()
                .map(Writing::getNextId)
                .toList();
        copy.removeAll(nextWritings);
        return copy.get(FIRST_WRITING_INDEX);
    }

    public void modifyCategoryName(final Long memberId, final Long categoryId, final CategoryModifyRequest request) {
        final Member findMember = findMember(memberId);
        final Category findCategory = findCategory(findMember.getId(), categoryId);
        final Category basicCategory = findBasicCategoryByMemberId(memberId);
        validateBasicCategory(basicCategory, findCategory);
        final CategoryName categoryName = new CategoryName(request.categoryName());
        validateCategoryName(categoryName);

        findCategory.changeName(categoryName);
    }

    public void removeCategory(final Long memberId, final Long categoryId) {
        final Member findMember = findMember(memberId);
        final Category findCategory = findCategory(findMember.getId(), categoryId);
        final Category basicCategory = findBasicCategoryByMemberId(memberId);
        validateBasicCategory(basicCategory, findCategory);
        final List<Writing> trashedWritingInCategory = writingRepository.findAllByMemberIdAndCategoryIdAndStatusIsTrashedAndDeleted(memberId, categoryId);
        for (final Writing writing : trashedWritingInCategory) {
            writing.changeCategory(basicCategory);
        }
        transferToBasicCategory(basicCategory, findCategory);
        deleteCategory(findCategory);
    }

    private void transferToBasicCategory(final Category basicCategory, final Category findCategory) {
        if (haveWritingsCategory(findCategory)) {
            final List<Writing> findWritings = writingRepository.findAllByCategoryId(findCategory.getId());
            final Long firstWritingId = findFirstWritingId(findWritings);
            if (haveWritingsCategory(basicCategory)) {
                final Writing lastWritingInBasicCategory = findLastWritingInCategory(basicCategory);
                lastWritingInBasicCategory.changeNextWritingId(firstWritingId);
            }
            findWritings.forEach(writing -> writing.changeCategory(basicCategory));
        }
    }

    private boolean haveWritingsCategory(final Category category) {
        return !writingRepository.findAllByCategoryId(category.getId()).isEmpty();
    }

    private void deleteCategory(final Category findCategory) {
        final Category nextCategory = findCategory.getNextCategory();
        final Category preCategory = findPreCategory(findCategory.getId());
        findCategory.changeNextCategoryNull();
        categoryRepository.flush();
        preCategory.changeNextCategory(nextCategory);
        categoryRepository.delete(findCategory);
    }

    public void modifyCategoryOrder(final Long memberId, final Long categoryId, final CategoryModifyRequest request) {
        final Member member = findMember(memberId);
        final Long nextCategoryId = request.nextCategoryId();
        final Category source = findCategory(member.getId(), categoryId);
        final Category basicCategory = findBasicCategoryByMemberId(member.getId());
        validateBasicCategory(basicCategory, source);
        deleteCategoryOrder(source);
        addCategoryOrder(nextCategoryId, source, member.getId());
    }

    private void deleteCategoryOrder(final Category category) {
        final Category nextCategory = category.getNextCategory();
        category.changeNextCategoryNull();
        final Category preCategory = findPreCategory(category.getId());
        preCategory.changeNextCategory(nextCategory);
    }

    private void addCategoryOrder(final Long nextCategoryId, final Category category, final Long memberId) {
        final Category preCategory;
        if (nextCategoryId == LAST_WRITING_FLAG) {
            preCategory = findLastCategoryByMemberId(memberId);
        } else {
            preCategory = findPreCategory(nextCategoryId);
        }
        preCategory.changeNextCategory(category);
        if (nextCategoryId != LAST_WRITING_FLAG) {
            final Category nextCategory = findCategory(memberId, nextCategoryId);
            category.changeNextCategory(nextCategory);
        }
    }

    private void validateBasicCategory(final Category basicCategory, final Category category) {
        if (basicCategory.equals(category)) {
            throw new InvalidBasicCategoryException(category.getId());
        }
    }

    private Category findLastCategoryByMemberId(final Long memberId) {
        return categoryRepository.findLastCategoryByMemberId(memberId)
                .orElseThrow(IllegalStateException::new);
    }

    private Category findBasicCategoryByMemberId(final Long memberId) {
        return categoryRepository.findFirstByMemberId(memberId)
                .orElseThrow(IllegalStateException::new);
    }

    private Category findPreCategory(final Long categoryId) {
        return categoryRepository.findPreCategoryByCategoryId(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    private Writing findLastWritingInCategory(final Category findCategory) {
        return writingRepository.findLastWritingByCategoryId(findCategory.getId())
                .orElseThrow(IllegalStateException::new);
    }

    private Member findMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private Category findCategory(final Long memberId, final Long categoryId) {
        return categoryRepository.findByIdAndMemberId(categoryId, memberId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }
}
