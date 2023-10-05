package org.donggle.backend.application.service.category;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.concurrent.NoConcurrentExecution;
import org.donggle.backend.application.service.request.CategoryAddRequest;
import org.donggle.backend.application.service.request.CategoryModifyRequest;
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

import static org.donggle.backend.domain.writing.WritingStatus.ACTIVE;
import static org.donggle.backend.domain.writing.WritingStatus.DELETED;
import static org.donggle.backend.domain.writing.WritingStatus.TRASHED;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private static final int LAST_CATEGORY_FLAG = -1;
    private static final int FIRST_WRITING_INDEX = 0;

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final WritingRepository writingRepository;

    @NoConcurrentExecution
    public Long addCategory(final Long memberId, final CategoryAddRequest request) {
        final Member findMember = findMember(memberId);
        final CategoryName categoryName = new CategoryName(request.categoryName());
        validateCategoryName(memberId, categoryName);
        final Category category = Category.of(categoryName, findMember);
        final Category lastCategory = findLastCategoryByMemberId(memberId);
        final Category savedCategory = categoryRepository.save(category);
        lastCategory.changeNextCategory(savedCategory);
        return savedCategory.getId();
    }

    private void validateCategoryName(final Long memberId, final CategoryName categoryName) {
        if (categoryRepository.existsByMemberIdAndCategoryName(memberId, categoryName)) {
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
        final List<Category> categories = categoryRepository.findAllByMemberId(memberId);
        final List<Category> sortedCategories = sortCategory(categories, findBasicCategory(categories));
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
        final List<Writing> findWritings = writingRepository.findAllByCategoryIdAndStatus(findCategory.getId(), ACTIVE);
        if (findWritings.isEmpty()) {
            return CategoryWritingsResponse.of(findCategory, Collections.emptyList());
        }
        final Writing firstWriting = findFirstWriting(findWritings);
        final List<Writing> sortedWriting = sortWriting(findWritings, firstWriting);
        final List<WritingSimpleResponse> writingSimpleResponses = sortedWriting.stream()
                .map(WritingSimpleResponse::from)
                .toList();
        return CategoryWritingsResponse.of(findCategory, writingSimpleResponses);
    }

    private Writing findFirstWriting(final List<Writing> findWritings) {
        final List<Writing> copy = new ArrayList<>(findWritings);
        final List<Writing> nextWritings = findWritings.stream()
                .map(Writing::getNextWriting)
                .toList();
        copy.removeAll(nextWritings);
        return copy.get(FIRST_WRITING_INDEX);
    }

    private List<Writing> sortWriting(final List<Writing> findWritings, final Writing firstWriting) {
        final Map<Writing, Writing> writingMap = new LinkedHashMap<>();
        for (final Writing findWriting : findWritings) {
            writingMap.put(findWriting, findWriting.getNextWriting());
        }
        final List<Writing> sortedWriting = new ArrayList<>();
        sortedWriting.add(firstWriting);
        Writing targetWriting = firstWriting;
        while (Objects.nonNull(targetWriting.getNextWriting())) {
            targetWriting = writingMap.get(targetWriting);
            sortedWriting.add(targetWriting);
        }
        return sortedWriting;
    }

    public void modifyCategoryName(final Long memberId, final Long categoryId, final CategoryModifyRequest request) {
        final Member findMember = findMember(memberId);
        final Category findCategory = findCategory(findMember.getId(), categoryId);
        final Category basicCategory = findBasicCategoryByMemberId(memberId);
        validateBasicCategory(basicCategory, findCategory);
        final CategoryName categoryName = new CategoryName(request.categoryName());
        validateCategoryName(memberId, categoryName);

        findCategory.changeName(categoryName);
    }

    public void removeCategory(final Long memberId, final Long categoryId) {
        final Member findMember = findMember(memberId);
        final Category findCategory = findCategory(findMember.getId(), categoryId);
        final Category basicCategory = findBasicCategoryByMemberId(memberId);
        validateBasicCategory(basicCategory, findCategory);
        final List<Writing> trashedAndDeletedWritingsInCategory = writingRepository.findAllByMemberIdAndCategoryIdInStatuses(memberId, categoryId, List.of(TRASHED, DELETED));
        for (final Writing writing : trashedAndDeletedWritingsInCategory) {
            writing.changeCategory(basicCategory);
        }
        transferToBasicCategory(basicCategory, findCategory);
        deleteCategory(findCategory);
    }

    private void transferToBasicCategory(final Category basicCategory, final Category findCategory) {
        if (haveWritingsCategory(findCategory)) {
            final List<Writing> findWritings = writingRepository.findAllByCategoryIdAndStatus(findCategory.getId(), ACTIVE);
            final Writing firstWritingInCategory = findFirstWriting(findWritings);
            if (haveWritingsCategory(basicCategory)) {
                final Writing lastWritingInBasicCategory = findLastWritingInCategory(basicCategory);
                lastWritingInBasicCategory.changeNextWriting(firstWritingInCategory);
            }
            findWritings.forEach(writing -> writing.changeCategory(basicCategory));
        }
    }

    private boolean haveWritingsCategory(final Category category) {
        return !writingRepository.findAllByCategoryIdAndStatus(category.getId(), ACTIVE).isEmpty();
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
        final Long targetCategoryId = request.nextCategoryId();
        final Category source = findCategory(memberId, categoryId);
        validateAuthorization(source, memberId);
        final Category basicCategory = findBasicCategoryByMemberId(memberId);
        validateModifyCategoryOrder(basicCategory, categoryId, request.nextCategoryId());
        if (isInValidRequest(source, request)) {
            return;
        }
        final Category nextCategory = source.getNextCategory();
        final Category lastCategory = findLastCategoryByMemberId(memberId);
        source.changeNextCategoryNull();
        final Category preCategory = findPreCategory(source.getId());
        preCategory.changeNextCategory(nextCategory);

        if (targetCategoryId == LAST_CATEGORY_FLAG) {
            lastCategory.changeNextCategory(source);
        } else {
            final Category targetCategory = findCategory(memberId, targetCategoryId);
            final Category targetPreCategory = findPreCategory(targetCategoryId);
            targetPreCategory.changeNextCategory(source);
            categoryRepository.flush();
            source.changeNextCategory(targetCategory);
        }
    }

    private void validateAuthorization(final Category category, final Long memberId) {
        if (!category.getMember().getId().equals(memberId)) {
            throw new CategoryNotFoundException(category.getId());
        }
    }

    private void validateModifyCategoryOrder(final Category basicCategory, final Long categoryId, final Long targetCategoryId) {
        if (isMovingBasicCategory(basicCategory, categoryId, targetCategoryId)) {
            throw new InvalidBasicCategoryException(categoryId, targetCategoryId);
        }
    }

    private boolean isMovingBasicCategory(final Category basicCategory, final Long categoryId, final Long targetCategoryId) {
        return Objects.equals(basicCategory.getId(), categoryId) ||
                Objects.equals(basicCategory.getId(), targetCategoryId);
    }

    private boolean isInValidRequest(final Category source, final CategoryModifyRequest request) {
        return isAlreadyLast(source, request) ||
                isSamePosition(source, request) ||
                isSelfPointing(source, request);
    }

    private boolean isAlreadyLast(final Category source, final CategoryModifyRequest request) {
        return source.getNextCategory() == null && request.nextCategoryId() == LAST_CATEGORY_FLAG;
    }

    private boolean isSamePosition(final Category source, final CategoryModifyRequest request) {
        return source.getNextCategory() != null &&
                Objects.equals(source.getNextCategory().getId(), request.nextCategoryId());
    }

    private boolean isSelfPointing(final Category source, final CategoryModifyRequest request) {
        return Objects.equals(source.getId(), request.nextCategoryId());
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

    private Category findBasicCategory(final List<Category> categories) {
        return categories.stream()
                .filter(category -> category.getCategoryName().getName().equals("기본"))
                .findFirst()
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
