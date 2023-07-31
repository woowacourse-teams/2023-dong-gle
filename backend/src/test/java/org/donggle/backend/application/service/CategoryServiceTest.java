package org.donggle.backend.application.service;

import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.ui.CategoryAddRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
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
    @DisplayName("카테고리 추가를 테스트")
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
}
