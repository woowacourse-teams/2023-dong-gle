package org.donggle.backend.application.service;

import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.request.CategoryAddRequest;
import org.donggle.backend.application.service.request.CategoryModifyRequest;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.category.CategoryName;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.exception.business.DuplicateCategoryNameException;
import org.donggle.backend.exception.business.EmptyCategoryNameException;
import org.donggle.backend.exception.business.InvalidBasicCategoryException;
import org.donggle.backend.exception.business.OverLengthCategoryNameException;
import org.donggle.backend.ui.response.CategoryListResponse;
import org.donggle.backend.ui.response.CategoryResponse;
import org.donggle.backend.ui.response.CategoryWritingsResponse;
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
        assertThatThrownBy(() -> categoryService.modifyCategoryName(1L, 1L, new CategoryModifyRequest("기본22222", null)))
                .isInstanceOf(InvalidBasicCategoryException.class);
    }

    @Test
    @DisplayName("카테고리 이름 수정 테스트")
    void modifyCategory() {
        //given
        final Long savedCategoryId = categoryService.addCategory(1L, new CategoryAddRequest("두 번째 카테고리"));

        //when
        categoryService.modifyCategoryName(1L, savedCategoryId, new CategoryModifyRequest("수정된 카테고리", null));
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
        final CategoryListResponse categoryListResponse = categoryService.findAll(1L);
        final List<CategoryResponse> categories = categoryListResponse.categories();

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
        writingRepository.delete(writingRepository.findById(1L).get());

        //when
        categoryService.removeCategory(1L, secondId);
        final List<Category> categories = categoryRepository.findAllByMemberId(1L);

        categoryRepository.flush();
        writingRepository.flush();

        final Writing writing = writingRepository.findAllByMemberIdAndCategoryIdAndStatusIsTrashedAndDeleted(1L, 1L).get(0);

        //then
        assertAll(
                () -> assertThat(categories).hasSize(1),
                () -> assertThat(categories.get(0).getCategoryName()).isEqualTo(new CategoryName("기본")),
                () -> assertThat(categories.get(0).getNextCategory()).isNull(),
                () -> assertThat(writing.getCategory()).isEqualTo(categories.get(0)),
                () -> assertThat(writing.getNextWriting()).isNull()
        );
    }

    @Test
    @DisplayName("비어있는 카테고리 삭제 테스트")
    void removeCategoryWithEmptyCategory() {
        //given
        final Long secondId = categoryService.addCategory(1L, new CategoryAddRequest("두 번째 카테고리"));

        //when
        categoryService.removeCategory(1L, secondId);
        final List<Category> categories = categoryRepository.findAllByMemberId(1L);
        categoryRepository.flush();

        //then
        assertAll(
                () -> assertThat(categories).hasSize(1),
                () -> assertThat(categories.get(0).getCategoryName()).isEqualTo(new CategoryName("기본")),
                () -> assertThat(categories.get(0).getNextCategory()).isNull()
        );
    }

    @Test
    @DisplayName("카테고리 글 목록 조회 테스트")
    void findAllWritingsById() {
        //given
        //when
        final CategoryWritingsResponse response = categoryService.findAllWritings(1L, 1L);
        final Writing findWriting = writingRepository.findById(1L).get();

        //then
        assertAll(
                () -> assertThat(response.writings()).hasSize(1),
                () -> assertThat(response.categoryName()).isEqualTo("기본"),
                () -> assertThat(response.writings().get(0).id()).isEqualTo(findWriting.getId()),
                () -> assertThat(response.writings().get(0).title()).isEqualTo(findWriting.getTitleValue())
        );
    }

    @Test
    @DisplayName("비어있는 카테고리 글 목록 조회 테스트")
    void findAllWritingsByIdWithEmptyCategory() {
        //given
        final Long secondId = categoryService.addCategory(1L, new CategoryAddRequest("두 번째 카테고리"));

        //when
        final CategoryWritingsResponse response = categoryService.findAllWritings(1L, secondId);

        //then
        assertAll(
                () -> assertThat(response.writings()).hasSize(0),
                () -> assertThat(response.writings()).isEmpty(),
                () -> assertThat(response.categoryName()).isEqualTo("두 번째 카테고리")
        );
    }

    @Test
    @DisplayName("카테고리 순서 수정 테스트")
    void modifyCategoryOrder() {
        //given
        final Long secondId = categoryService.addCategory(1L, new CategoryAddRequest("두 번째 카테고리"));
        final Long thirdId = categoryService.addCategory(1L, new CategoryAddRequest("세 번째 카테고리"));

        //when
        categoryService.modifyCategoryOrder(1L, thirdId, new CategoryModifyRequest(null, secondId));

        //then
        final CategoryListResponse response = categoryService.findAll(1L);
        assertAll(
                () -> assertThat(response.categories()).hasSize(3),
                () -> assertThat(response.categories()).containsExactly(
                        new CategoryResponse(1L, "기본"),
                        new CategoryResponse(thirdId, "세 번째 카테고리"),
                        new CategoryResponse(secondId, "두 번째 카테고리")
                )
        );
    }

    @Test
    @DisplayName("카테고리 이름 중복 예외")
    void duplicateCategoryName() {
        //given
        categoryService.addCategory(1L, new CategoryAddRequest("기본 카테고리"));

        //when

        //then
        assertThatThrownBy(() -> categoryService.addCategory(1L, new CategoryAddRequest("기본 카테고리")))
                .isInstanceOf(DuplicateCategoryNameException.class)
                .hasMessage("이미 존재하는 카테고리 이름입니다.");
    }

    @Test
    @DisplayName("카테고리 빈 이름 예외")
    void emptyCategoryName() {
        //given

        //when

        //then
        assertThatThrownBy(() -> categoryService.addCategory(1L, new CategoryAddRequest("")))
                .isInstanceOf(EmptyCategoryNameException.class)
                .hasMessage("카테고리 이름은 빈 값이 될 수 없습니다.");
    }

    @Test
    @DisplayName("카테고리 공백 이름 예외")
    void blankCategoryName() {
        //given

        //when

        //then
        assertThatThrownBy(() -> categoryService.addCategory(1L, new CategoryAddRequest(" ")))
                .isInstanceOf(EmptyCategoryNameException.class)
                .hasMessage("카테고리 이름은 빈 값이 될 수 없습니다.");
    }

    @Test
    @DisplayName("카테고리 이름 빈 이름 수정 예외")
    void modifyCategoryNameWithEmptyName() {
        //given
        final Long secondId = categoryService.addCategory(1L, new CategoryAddRequest("두 번째 카테고리"));

        //when

        //then
        assertThatThrownBy(() -> categoryService.modifyCategoryName(1L, secondId, new CategoryModifyRequest("", null)))
                .isInstanceOf(EmptyCategoryNameException.class)
                .hasMessage("카테고리 이름은 빈 값이 될 수 없습니다.");
    }

    @Test
    @DisplayName("카테고리 긴 이름 예외")
    void longCategoryName() {
        //given

        //when

        //then
        assertThatThrownBy(() -> categoryService.addCategory(1L, new CategoryAddRequest("123456789012345678901234567890123456789012345678901234567890")))
                .isInstanceOf(OverLengthCategoryNameException.class)
                .hasMessage("카테고리 이름은 30자를 넘을 수 없습니다.");
    }

    @Test
    @DisplayName("기본 카테고리 삭제 예외")
    void removeDefaultCategory() {
        //given

        //when

        //then
        assertThatThrownBy(() -> categoryService.removeCategory(1L, 1L))
                .isInstanceOf(InvalidBasicCategoryException.class)
                .hasMessage("기본 카테고리는 변경이 불가합니다.");
    }
}
