package org.donggle.backend.application.service;

import org.assertj.core.api.Assertions;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.service.category.CategoryService;
import org.donggle.backend.application.service.request.CategoryModifyRequest;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.category.CategoryName;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberName;
import org.donggle.backend.domain.oauth.SocialType;
import org.donggle.backend.exception.business.InvalidBasicCategoryException;
import org.donggle.backend.ui.response.CategoryListResponse;
import org.donggle.backend.ui.response.CategoryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest
class ModifyCategoryOrderTest {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private Member member;
    private Category basicCategory;
    private Category secondCategory;
    private Category thirdCategory;
    private Category fourthCategory;

    @BeforeEach
    void setUp() {
        //given
        member = memberRepository.save(Member.of(
                new MemberName("테스트 멤버"),
                1234L,
                SocialType.KAKAO
        ));

        basicCategory = categoryRepository.save(Category.of(new CategoryName("기본"), member));
        secondCategory = categoryRepository.save(Category.of(new CategoryName("두 번째 카테고리"), member));
        thirdCategory = categoryRepository.save(Category.of(new CategoryName("세 번째 카테고리"), member));
        fourthCategory = categoryRepository.save(Category.of(new CategoryName("네 번째 카테고리"), member));

        basicCategory.changeNextCategory(secondCategory);
        secondCategory.changeNextCategory(thirdCategory);
        thirdCategory.changeNextCategory(fourthCategory);
    }

    @Test
    @DisplayName("[1, '2', 3, 4] -> [1, 3, '2', 4]")
    void modifyCategoryOrder2() {
        //when
        categoryService.modifyCategoryOrder(member.getId(), secondCategory.getId(), new CategoryModifyRequest(null, fourthCategory.getId()));

        //then
        final CategoryListResponse response = categoryService.findAll(member.getId());
        assertAll(
                () -> assertThat(response.categories()).hasSize(4),
                () -> assertThat(response.categories()).containsExactly(
                        new CategoryResponse(basicCategory.getId(), "기본"),
                        new CategoryResponse(thirdCategory.getId(), "세 번째 카테고리"),
                        new CategoryResponse(secondCategory.getId(), "두 번째 카테고리"),
                        new CategoryResponse(fourthCategory.getId(), "네 번째 카테고리")
                )
        );
    }

    @Test
    @DisplayName("[1, '2', 3, 4] -> [1, 3, 4, '2']")
    void modifyCategoryOrder3() {
        //when
        categoryService.modifyCategoryOrder(member.getId(), secondCategory.getId(), new CategoryModifyRequest(null, -1L));

        //then
        final CategoryListResponse response = categoryService.findAll(member.getId());
        assertAll(
                () -> assertThat(response.categories()).hasSize(4),
                () -> assertThat(response.categories()).containsExactly(
                        new CategoryResponse(basicCategory.getId(), "기본"),
                        new CategoryResponse(thirdCategory.getId(), "세 번째 카테고리"),
                        new CategoryResponse(fourthCategory.getId(), "네 번째 카테고리"),
                        new CategoryResponse(secondCategory.getId(), "두 번째 카테고리")
                )
        );
    }

    @Test
    @DisplayName("[1, 2, '3', 4] -> [1, '3', 2, 4]")
    void modifyCategoryOrder4() {
        //when
        categoryService.modifyCategoryOrder(member.getId(), thirdCategory.getId(), new CategoryModifyRequest(null, secondCategory.getId()));

        //then
        final CategoryListResponse response = categoryService.findAll(member.getId());
        assertAll(
                () -> assertThat(response.categories()).hasSize(4),
                () -> assertThat(response.categories()).containsExactly(
                        new CategoryResponse(basicCategory.getId(), "기본"),
                        new CategoryResponse(thirdCategory.getId(), "세 번째 카테고리"),
                        new CategoryResponse(secondCategory.getId(), "두 번째 카테고리"),
                        new CategoryResponse(fourthCategory.getId(), "네 번째 카테고리")
                )
        );
    }

    @Test
    @DisplayName("[1, 2, 3, '4'] -> [1, '4', 2, 3]")
    void modifyCategoryOrder5() {
        //when
        categoryService.modifyCategoryOrder(member.getId(), fourthCategory.getId(), new CategoryModifyRequest(null, secondCategory.getId()));

        //then
        final CategoryListResponse response = categoryService.findAll(member.getId());
        assertAll(
                () -> assertThat(response.categories()).hasSize(4),
                () -> assertThat(response.categories()).containsExactly(
                        new CategoryResponse(basicCategory.getId(), "기본"),
                        new CategoryResponse(fourthCategory.getId(), "네 번째 카테고리"),
                        new CategoryResponse(secondCategory.getId(), "두 번째 카테고리"),
                        new CategoryResponse(thirdCategory.getId(), "세 번째 카테고리")
                )
        );
    }

    @Nested
    @DisplayName("예외적인 요청에 대한 처리")
    class ExceptionalCategoryOrderModifyTest {
        @Test
        @DisplayName("['1', 2, 3, 4] -> [2, 3, 4, '1']")
        void exceptionalModify1() {
            //when, then
            Assertions.assertThatThrownBy(() -> categoryService.modifyCategoryOrder(member.getId(), basicCategory.getId(), new CategoryModifyRequest(null, -1L)))
                    .isInstanceOf(InvalidBasicCategoryException.class);
        }

        @Test
        @DisplayName("[1, 2, '3', 4] -> ['3', 1, 2, 4]")
        void exceptionalModify2() {
            //when
            Assertions.assertThatThrownBy(() -> categoryService.modifyCategoryOrder(member.getId(), thirdCategory.getId(), new CategoryModifyRequest(null, basicCategory.getId())))
                    .isInstanceOf(InvalidBasicCategoryException.class);
        }

        @Test
        @DisplayName("[1, '2', 3, 4] -> [1, '2', 3, 4]")
        void exceptionalModify3() {
            //when
            categoryService.modifyCategoryOrder(member.getId(), secondCategory.getId(), new CategoryModifyRequest(null, thirdCategory.getId()));

            //then
            final CategoryListResponse response = categoryService.findAll(member.getId());
            assertAll(
                    () -> assertThat(response.categories()).hasSize(4),
                    () -> assertThat(response.categories()).containsExactly(
                            new CategoryResponse(basicCategory.getId(), "기본"),
                            new CategoryResponse(secondCategory.getId(), "두 번째 카테고리"),
                            new CategoryResponse(thirdCategory.getId(), "세 번째 카테고리"),
                            new CategoryResponse(fourthCategory.getId(), "네 번째 카테고리")
                    )
            );
        }

        @Test
        @DisplayName("[1, '2', 3, 4] -> [1, '2', 3, 4] (자기 자신을 참조)")
        void exceptionalModify4() {
            //when
            categoryService.modifyCategoryOrder(member.getId(), secondCategory.getId(), new CategoryModifyRequest(null, secondCategory.getId()));

            //then
            final CategoryListResponse response = categoryService.findAll(member.getId());
            assertAll(
                    () -> assertThat(response.categories()).hasSize(4),
                    () -> assertThat(response.categories()).containsExactly(
                            new CategoryResponse(basicCategory.getId(), "기본"),
                            new CategoryResponse(secondCategory.getId(), "두 번째 카테고리"),
                            new CategoryResponse(thirdCategory.getId(), "세 번째 카테고리"),
                            new CategoryResponse(fourthCategory.getId(), "네 번째 카테고리")
                    )
            );
        }

        @Test
        @DisplayName("[1, 2, 3, '4'] -> [1, 2, 3, '4']")
        void exceptionalModify5() {
            //when
            categoryService.modifyCategoryOrder(member.getId(), fourthCategory.getId(), new CategoryModifyRequest(null, -1L));

            //then
            final CategoryListResponse response = categoryService.findAll(member.getId());
            assertAll(
                    () -> assertThat(response.categories()).hasSize(4),
                    () -> assertThat(response.categories()).containsExactly(
                            new CategoryResponse(basicCategory.getId(), "기본"),
                            new CategoryResponse(secondCategory.getId(), "두 번째 카테고리"),
                            new CategoryResponse(thirdCategory.getId(), "세 번째 카테고리"),
                            new CategoryResponse(fourthCategory.getId(), "네 번째 카테고리")
                    )
            );
        }
    }
}
