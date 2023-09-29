package org.donggle.backend.application.service;

import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.category.CategoryService;
import org.donggle.backend.application.service.request.CategoryModifyRequest;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.exception.business.InvalidBasicCategoryException;
import org.donggle.backend.support.fix.CategoryFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ModifyCategoryOrderTest {

    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private WritingRepository writingRepository;


    @Test
    @DisplayName("[1, '2', 3] -> [1, 3, '2']")
    void modifyCategoryOrder2() {
        // given
        final Long memberId = 1L;
        final Long movingCategoryId = 2L;
        final Long targetCategoryId = 3L;
        final CategoryModifyRequest request = new CategoryModifyRequest("동글", targetCategoryId);
        final Member member = mock(Member.class);
        final Category firstCategory = mock(Category.class);
        final Category movingCategory = mock(Category.class);
        final Category thirdCategory = mock(Category.class);
        final Category lastCategory = mock(Category.class);

        given(categoryRepository.findByIdAndMemberId(movingCategoryId, memberId)).willReturn(Optional.of(movingCategory));
        given(movingCategory.getNextCategory()).willReturn(thirdCategory);
        given(categoryRepository.findFirstByMemberId(memberId)).willReturn(Optional.of(firstCategory));
        given(categoryRepository.findLastCategoryByMemberId(memberId)).willReturn(Optional.of(lastCategory));
        given(categoryRepository.findByIdAndMemberId(targetCategoryId, memberId)).willReturn(Optional.of(thirdCategory));
        given(categoryRepository.findPreCategoryByCategoryId(movingCategoryId)).willReturn(Optional.of(firstCategory));
        given(categoryRepository.findPreCategoryByCategoryId(targetCategoryId)).willReturn(Optional.of(movingCategory));
        given(movingCategory.getId()).willReturn(2L);
        given(movingCategory.getMember()).willReturn(member);
        given(member.getId()).willReturn(1L);
        // when
        categoryService.modifyCategoryOrder(memberId, movingCategoryId, request);

        // then
        then(movingCategory).should().changeNextCategoryNull();
        then(firstCategory).should().changeNextCategory(thirdCategory);
        then(categoryRepository).should().flush();
        then(movingCategory).should().changeNextCategory(thirdCategory);
    }


    @Test
    @DisplayName("[1, 2, '3'] -> ['3', 1, 2]")
    void modifyCategoryOrder4() {
        // given
        final Long memberId = 1L;
        final Long movingCategoryId = 3L;
        final Long targetCategoryId = 1L;
        final CategoryModifyRequest request = new CategoryModifyRequest("동글", targetCategoryId);
        final Member member = mock(Member.class);
        final Category firstCategory = mock(Category.class);
        final Category secondCategory = mock(Category.class);
        final Category movingCategory = mock(Category.class);

        given(categoryRepository.findByIdAndMemberId(movingCategoryId, memberId)).willReturn(Optional.of(movingCategory));
        given(movingCategory.getNextCategory()).willReturn(null);
        given(categoryRepository.findFirstByMemberId(memberId)).willReturn(Optional.of(firstCategory));
        given(categoryRepository.findByIdAndMemberId(targetCategoryId, memberId)).willReturn(Optional.of(firstCategory));
        given(categoryRepository.findPreCategoryByCategoryId(movingCategoryId)).willReturn(Optional.of(secondCategory));
        given(categoryRepository.findPreCategoryByCategoryId(targetCategoryId)).willReturn(Optional.of(movingCategory));
        given(categoryRepository.findLastCategoryByMemberId(memberId)).willReturn(Optional.of(movingCategory));
        given(movingCategory.getId()).willReturn(3L);
        given(movingCategory.getMember()).willReturn(member);
        given(member.getId()).willReturn(1L);

        // when
        categoryService.modifyCategoryOrder(memberId, movingCategoryId, request);

        // then
        then(secondCategory).should().changeNextCategory(null);
        then(movingCategory).should().changeNextCategory(firstCategory);
        then(categoryRepository).should().flush();
    }

    @Test
    @DisplayName("[1, 2, '3'] -> [1, '3', 2]")
    void modifyCategoryOrder5() {
        // given
        final Long memberId = 1L;
        final Long movingCategoryId = 3L;
        final Long targetCategoryId = 2L;
        final CategoryModifyRequest request = new CategoryModifyRequest("동글", targetCategoryId);
        final Member member = mock(Member.class);
        final Category firstCategory = mock(Category.class);
        final Category secondCategory = mock(Category.class);
        final Category movingCategory = mock(Category.class);

        given(categoryRepository.findByIdAndMemberId(movingCategoryId, memberId)).willReturn(Optional.of(movingCategory));
        given(movingCategory.getNextCategory()).willReturn(null);
        given(categoryRepository.findFirstByMemberId(memberId)).willReturn(Optional.of(firstCategory));
        given(categoryRepository.findByIdAndMemberId(targetCategoryId, memberId)).willReturn(Optional.of(secondCategory));
        given(categoryRepository.findPreCategoryByCategoryId(movingCategoryId)).willReturn(Optional.of(secondCategory));
        given(categoryRepository.findPreCategoryByCategoryId(targetCategoryId)).willReturn(Optional.of(firstCategory));
        given(categoryRepository.findLastCategoryByMemberId(memberId)).willReturn(Optional.of(movingCategory));
        given(movingCategory.getId()).willReturn(3L);
        given(movingCategory.getMember()).willReturn(member);
        given(member.getId()).willReturn(1L);

        // when
        categoryService.modifyCategoryOrder(memberId, movingCategoryId, request);

        // then
        then(firstCategory).should().changeNextCategory(movingCategory);
        then(movingCategory).should().changeNextCategory(secondCategory);
        then(categoryRepository).should().flush();
    }

    @Nested
    @DisplayName("예외적인 요청에 대한 처리")
    class ExceptionalCategoryOrderModifyTest {
        @Test
        @DisplayName("['1', 2, 3] -> [2, 3, '1']")
        void exceptionalModify1() {
            final Long memberId = 10L;
            final Long movingCategoryId = 1L;
            final Long targetCategoryId = 3L;
            final CategoryModifyRequest request = new CategoryModifyRequest("동글", targetCategoryId);
            final Category movingCategory = CategoryFixture.basicCategory;
            given(categoryRepository.findByIdAndMemberId(movingCategoryId, memberId)).willReturn(Optional.of(movingCategory));
            given(categoryRepository.findFirstByMemberId(memberId)).willReturn(Optional.of(movingCategory));

            //when, then
            assertThatThrownBy(
                    () -> categoryService.modifyCategoryOrder(memberId, movingCategoryId, request)
            ).isInstanceOf(InvalidBasicCategoryException.class);
        }

        @Test
        @DisplayName("[1, 2, '3'] -> ['3', 1, 2]")
        void exceptionalModify2() {
            //when
            final Long memberId = 10L;
            final Long movingCategoryId = 3L;
            final Long targetCategoryId = 1L;
            final CategoryModifyRequest request = new CategoryModifyRequest("동글", targetCategoryId);
            final Category movingCategory = CategoryFixture.categoryBy(3L);
            given(categoryRepository.findByIdAndMemberId(movingCategoryId, memberId)).willReturn(Optional.of(movingCategory));
            given(categoryRepository.findFirstByMemberId(memberId)).willReturn(Optional.of(movingCategory));

            //when, then
            assertThatThrownBy(
                    () -> categoryService.modifyCategoryOrder(memberId, movingCategoryId, request)
            ).isInstanceOf(InvalidBasicCategoryException.class);
        }
    }
}
