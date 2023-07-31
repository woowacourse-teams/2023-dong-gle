package org.donggle.backend.application.service;

import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.service.request.CategoryAddRequest;
import org.donggle.backend.application.service.request.CategoryModifyRequest;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.category.CategoryName;
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

    @Test
    @DisplayName("기본 카테고리 조회 테스트")
    void findBasicCategory() {
        //given
        final Category basicCategory = categoryRepository.findById(1L).get();

        //when
        final Category lastCategory = categoryRepository.findLastByMemberId(1L).get();

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
        final Category expectedSavedCategory = categoryRepository.findLastByMemberId(1L).get();

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
}
