package org.donggle.backend.application.service;

import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.request.CategoryAddRequest;
import org.donggle.backend.application.service.request.CategoryModifyRequest;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.category.CategoryName;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.exception.business.InvalidBasicCategoryException;
import org.donggle.backend.ui.response.CategoriesResponse;
import org.donggle.backend.ui.response.CategoryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private WritingRepository writingRepository;

    @Test
    @DisplayName("기본 카테고리 조회 테스트")
    void findBasicCategory() {
        //given
        final Category basicCategory = categoryRepository.findById(1L).get();

        //when
        final Category lastCategory = categoryRepository.findLastCategoryByMemberId(1L).get();

        //then
        assertThat(basicCategory).isEqualTo(lastCategory);
    }

    @Test
    @DisplayName("카테고리 추가 테스트")
    void addCategory() {
        //given
        //when
        final Long savedCategoryId = categoryService.addCategory(1L, new CategoryAddRequest("두 번째 카테고리"));

        final Category basicCategory = categoryRepository.findById(1L).get();
        final Category savedCategory = categoryRepository.findById(savedCategoryId).get();
        final Category expectedSavedCategory = categoryRepository.findLastCategoryByMemberId(1L).get();

        //then
        assertAll(
                () -> assertThat(basicCategory.getNextCategory()).isEqualTo(savedCategory),
                () -> assertThat(savedCategory).isEqualTo(expectedSavedCategory)
        );
    }

    @Test
    @DisplayName("기본 카테고리 수정 테스트")
    void modifyBasicCategory() {
        //given
        //when
        //then
        assertThatThrownBy(() -> categoryService.modifyCategory(1L, 1L, new CategoryModifyRequest("기본22222")))
                .isInstanceOf(InvalidBasicCategoryException.class);
    }

    @Test
    @DisplayName("카테고리 이름 수정 테스트")
    void modifyCategory() {
        //given
        final Long savedCategoryId = categoryService.addCategory(1L, new CategoryAddRequest("두 번째 카테고리"));

        //when
        categoryService.modifyCategory(1L, savedCategoryId, new CategoryModifyRequest("수정된 카테고리"));
        categoryRepository.flush();
        final Category modifyCategory = categoryRepository.findById(savedCategoryId).get();

        //then
        assertThat(modifyCategory.getCategoryName()).isEqualTo(new CategoryName("수정된 카테고리"));
    }

    @Test
    @DisplayName("카테고리 목록 조회 테스트")
    void findAll() {
        //given
        final Long secondId = categoryService.addCategory(1L, new CategoryAddRequest("두 번째 카테고리"));
        final Long thirdId = categoryService.addCategory(1L, new CategoryAddRequest("세 번째 카테고리"));

        //when
        final CategoriesResponse categoriesResponse = categoryService.findAll(1L);
        final List<CategoryResponse> categories = categoriesResponse.categories();

        //then
        assertAll(
                () -> assertThat(categories).hasSize(3),
                () -> assertThat(categories).containsExactly(
                        new CategoryResponse(1L, "기본"),
                        new CategoryResponse(secondId, "두 번째 카테고리"),
                        new CategoryResponse(thirdId, "세 번째 카테고리")
                )
        );
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void removeCategory() {
        //given
        final Long secondId = categoryService.addCategory(1L, new CategoryAddRequest("두 번째 카테고리"));
        final Category secondCategory = categoryRepository.findById(secondId).get();
        writingRepository.findById(1L).get()
                .changeCategory(secondCategory);

        //when
        categoryService.removeCategory(1L, secondId);
        final List<Category> categories = categoryRepository.findAllByMemberId(1L);

        categoryRepository.flush();
        writingRepository.flush();

        final Writing writing = writingRepository.findById(1L).get();

        //then
        assertAll(
                () -> assertThat(categories).hasSize(1),
                () -> assertThat(categories.get(0).getCategoryName()).isEqualTo(new CategoryName("기본")),
                () -> assertThat(categories.get(0).getNextCategory()).isNull(),
                () -> assertThat(writing.getCategory()).isEqualTo(categories.get(0))
        );
    }
}
