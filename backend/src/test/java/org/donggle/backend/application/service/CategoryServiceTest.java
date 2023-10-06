package org.donggle.backend.application.service;

import org.assertj.core.api.Assertions;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.category.CategoryService;
import org.donggle.backend.application.service.request.CategoryAddRequest;
import org.donggle.backend.application.service.request.CategoryModifyRequest;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.category.CategoryName;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.exception.business.DuplicateCategoryNameException;
import org.donggle.backend.exception.business.EmptyCategoryNameException;
import org.donggle.backend.exception.business.InvalidBasicCategoryException;
import org.donggle.backend.exception.business.OverLengthCategoryNameException;
import org.donggle.backend.exception.notfound.CategoryNotFoundException;
import org.donggle.backend.support.fix.WritingFixture;
import org.donggle.backend.ui.response.CategoryResponse;
import org.donggle.backend.ui.response.CategoryWritingsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.donggle.backend.domain.writing.WritingStatus.ACTIVE;
import static org.donggle.backend.domain.writing.WritingStatus.DELETED;
import static org.donggle.backend.domain.writing.WritingStatus.TRASHED;
import static org.donggle.backend.support.fix.MemberFixture.beaver_have_id;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    private WritingRepository writingRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(memberRepository, categoryRepository, writingRepository);
    }

    @Test
    @DisplayName("기본 카테고리 조회 테스트")
    void findBasicCategory() {
        //given
        final long memberId = 1L;
        final Category category = Category.of(new CategoryName("안녕"), beaver_have_id);
        given(categoryRepository.findLastCategoryByMemberId(memberId)).willReturn(Optional.of(category));
        //when
        final Category lastCategory = categoryRepository.findLastCategoryByMemberId(1L).get();

        //then
        assertThat(category).usingRecursiveComparison().isEqualTo(lastCategory);
    }

    @Test
    @DisplayName("카테고리 추가 테스트")
    void addCategory() {
        //given
        final long memberId = 10L;
        final String categoryName = "새 카테고리";
        final CategoryAddRequest request = new CategoryAddRequest(categoryName);
        final Category lastCategory = new Category(10L, new CategoryName("기존 카테고리"), null, beaver_have_id);
        final Category newCategory = new Category(11L, new CategoryName(categoryName), null, beaver_have_id);

        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver_have_id));
        given(categoryRepository.existsByMemberIdAndCategoryName(memberId, new CategoryName(categoryName))).willReturn(false);
        given(categoryRepository.findLastCategoryByMemberId(memberId)).willReturn(Optional.of(lastCategory));
        given(categoryRepository.save(any(Category.class))).willReturn(newCategory);

        //when
        final Long savedCategoryId = categoryService.addCategory(memberId, request);

        //then
        assertThat(savedCategoryId).isEqualTo(11L);
    }


    @Test
    @DisplayName("기본 카테고리 수정할 때 에러 테스트")
    void modifyBasicCategory() {
        //given
        final long memberId = 10L;
        final CategoryModifyRequest categoryModifyRequest = new CategoryModifyRequest("안녕", -1L);
        final Category basicCategory = new Category(1L, new CategoryName("기본"), null, beaver_have_id);
        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver_have_id));
        given(categoryRepository.findFirstByMemberId(memberId)).willReturn(Optional.of(basicCategory));
        given(categoryRepository.findByIdAndMemberId(1L, memberId)).willReturn(Optional.of(basicCategory));
        //when
        //then
        assertThatThrownBy(() -> categoryService.modifyCategoryName(10L, 1L, categoryModifyRequest)).isInstanceOf(InvalidBasicCategoryException.class);
    }

    @Test
    @DisplayName("카테고리 이름 수정 성공 테스트")
    void modifyCategoryName_Success() {
        // given
        final long memberId = 10L;
        final long categoryId = 1L;
        final String newName = "새 카테고리 이름";
        final CategoryModifyRequest request = new CategoryModifyRequest(newName, -1L);

        final Member member = beaver_have_id;
        final Category category = new Category(11L, new CategoryName(newName), null, member);
        final Category basicCategory = new Category(1L, new CategoryName("기본"), null, beaver_have_id);
        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
        given(categoryRepository.findByIdAndMemberId(categoryId, memberId)).willReturn(Optional.of(category));
        given(categoryRepository.findFirstByMemberId(memberId)).willReturn(Optional.of(basicCategory));

        // when
        // then
        assertDoesNotThrow(() -> categoryService.modifyCategoryName(memberId, categoryId, request));
    }

    @Test
    @DisplayName("카테고리를 찾지 못할 때 에러")
    void modifyCategoryName_not_found_category() {
        // given
        final long memberId = 10L;
        final long categoryId = 1L;
        final String newName = "새 카테고리 이름";
        final CategoryModifyRequest request = new CategoryModifyRequest(newName, -1L);

        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver_have_id));
        given(categoryRepository.findByIdAndMemberId(categoryId, memberId)).willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> categoryService.modifyCategoryName(memberId, categoryId, request)
        ).isInstanceOf(CategoryNotFoundException.class);
    }


    @Test
    @DisplayName("카테고리 목록 조회 테스트")
    void findAll() {
        //given
        final long memberId = 10L;
        final Member member = beaver_have_id;
        final Category category3 = new Category(3L, new CategoryName("세 번째 카테고리"), null, member);
        final Category category2 = new Category(2L, new CategoryName("두 번째 카테고리"), category3, member);
        final Category category1 = new Category(1L, new CategoryName("기본"), category2, member);

        given(categoryRepository.findAllByMemberId(memberId)).willReturn(List.of(category1, category2, category3));

        //when
        final List<CategoryResponse> result = categoryService.findAll(memberId).categories();

        //then
        assertThat(result).hasSize(3);
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void removeCategory() {
        // given
        final long memberId = 10L;
        final long categoryId = 2L;
        final String newName = "카테고리";
        final Category category = new Category(2L, new CategoryName(newName), null, beaver_have_id);
        final Category basicCategory = new Category(1L, new CategoryName("기본"), category, beaver_have_id);
        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver_have_id));
        given(categoryRepository.findByIdAndMemberId(categoryId, memberId)).willReturn(Optional.of(category));
        given(categoryRepository.findFirstByMemberId(memberId)).willReturn(Optional.of(basicCategory));
        given(categoryRepository.findPreCategoryByCategoryId(categoryId)).willReturn(Optional.of(basicCategory));
        given(writingRepository.findAllByMemberIdAndCategoryIdInStatuses(memberId, categoryId, List.of(TRASHED, DELETED)))
                .willReturn(WritingFixture.createWritings_ACTIVE());
        //when
        //then
        assertDoesNotThrow(() -> categoryService.removeCategory(memberId, categoryId));
    }

    @Test
    @DisplayName("카테고리 글 목록 조회 테스트")
    void findAllWritingsById() {
        // given
        final long memberId = 10L;
        final long categoryId = 2L;
        final String newName = "카테고리";
        final Category category = new Category(2L, new CategoryName(newName), null, beaver_have_id);
        given(categoryRepository.findByIdAndMemberId(categoryId, memberId)).willReturn(Optional.of(category));
        given(writingRepository.findAllByCategoryIdAndStatus(2L, ACTIVE))
                .willReturn(WritingFixture.createWritings_ACTIVE());
        //when
        final CategoryWritingsResponse result = categoryService.findAllWritings(memberId, categoryId);

        //then
        assertThat(result.writings().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("비어있는 카테고리 글 목록 조회 테스트")
    void findAllWritingsByIdWithEmptyCategory() {
        //given
        final long memberId = 10L;
        final long categoryId = 2L;
        final String newName = "카테고리";
        final Category category = new Category(2L, new CategoryName(newName), null, beaver_have_id);
        given(categoryRepository.findByIdAndMemberId(categoryId, memberId)).willReturn(Optional.of(category));
        given(writingRepository.findAllByCategoryIdAndStatus(2L, ACTIVE))
                .willReturn(List.of());
        //when
        final CategoryWritingsResponse result = categoryService.findAllWritings(memberId, categoryId);

        //then
        assertThat(result.writings().size()).isEqualTo(0);
    }


    @Test
    @DisplayName("카테고리 이름 중복 예외")
    void duplicateCategoryName() {
        //given
        final long memberId = 10L;
        final String categoryName = "기본 카테고리";
        final CategoryAddRequest request = new CategoryAddRequest(categoryName);

        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver_have_id));
        given(categoryRepository.existsByMemberIdAndCategoryName(memberId, new CategoryName(categoryName))).willReturn(true);

        //then
        assertThatThrownBy(
                () -> categoryService.addCategory(10L, request)
        ).isInstanceOf(DuplicateCategoryNameException.class).hasMessage("이미 존재하는 카테고리 이름입니다.");
    }

    @Test
    @DisplayName("카테고리 빈 이름 예외")
    void emptyCategoryName() {
        //given
        final long memberId = 10L;
        final String categoryName = "";
        final CategoryAddRequest request = new CategoryAddRequest(categoryName);

        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver_have_id));
        given(categoryRepository.existsByMemberIdAndCategoryName(memberId, new CategoryName(categoryName))).willReturn(false);

        //then
        assertThatThrownBy(() -> categoryService.addCategory(10L, request)).isInstanceOf(EmptyCategoryNameException.class).hasMessage("카테고리 이름은 빈 값이 될 수 없습니다.");
    }

    @Test
    @DisplayName("카테고리 공백 이름 예외")
    void blankCategoryName() {
        //given
        final long memberId = 10L;
        final String categoryName = " ";
        final CategoryAddRequest request = new CategoryAddRequest(categoryName);

        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver_have_id));
        given(categoryRepository.existsByMemberIdAndCategoryName(memberId, new CategoryName(categoryName))).willReturn(false);

        //when
        //then
        assertThatThrownBy(() -> categoryService.addCategory(10L, request)).isInstanceOf(EmptyCategoryNameException.class).hasMessage("카테고리 이름은 빈 값이 될 수 없습니다.");
    }

    @Test
    @DisplayName("카테고리 이름 빈 이름 수정 예외")
    void modifyCategoryNameWithEmptyName() {
        //given
        final long memberId = 10L;
        final long categoryId = 2L;
        final String categoryName = "";
        final CategoryModifyRequest request = new CategoryModifyRequest(categoryName, -1L);
        final Category category = new Category(2L, new CategoryName(categoryName), null, beaver_have_id);
        final Category basicCategory = new Category(1L, new CategoryName("기본"), null, beaver_have_id);

        given(categoryRepository.findFirstByMemberId(memberId)).willReturn(Optional.of(basicCategory));
        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver_have_id));
        given(categoryRepository.existsByMemberIdAndCategoryName(memberId, new CategoryName(categoryName))).willReturn(false);
        given(categoryRepository.findByIdAndMemberId(categoryId, memberId)).willReturn(Optional.of(category));

        //then
        assertThatThrownBy(() -> categoryService.modifyCategoryName(10L, 2L, request)).isInstanceOf(EmptyCategoryNameException.class).hasMessage("카테고리 이름은 빈 값이 될 수 없습니다.");
    }

    @Test
    @DisplayName("카테고리 긴 이름 예외")
    void longCategoryName() {
        //given
        final long memberId = 10L;
        final String categoryName = "새 카테고리새 카테고리새 카테고리새 카테고리새 카테고리새 카테고리새 카테고리새 카테고리새 카테고리새 카테고리새 카테고리";
        final CategoryAddRequest request = new CategoryAddRequest(categoryName);

        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver_have_id));
        given(categoryRepository.existsByMemberIdAndCategoryName(memberId, new CategoryName(categoryName))).willReturn(false);

        //when
        //then
        assertThatThrownBy(() -> categoryService.addCategory(10L, request)).isInstanceOf(OverLengthCategoryNameException.class).hasMessage("카테고리 이름은 30자를 넘을 수 없습니다.");
    }

    @Test
    @DisplayName("기본 카테고리 삭제 예외")
    void removeDefaultCategory() {
        //given
        final long memberId = 10L;
        final Category basicCategory = new Category(1L, new CategoryName("기본"), null, beaver_have_id);
        given(categoryRepository.findFirstByMemberId(memberId)).willReturn(Optional.of(basicCategory));
        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver_have_id));
        given(categoryRepository.findByIdAndMemberId(1L, memberId)).willReturn(Optional.of(basicCategory));

        //when
        //then
        assertThatThrownBy(() -> categoryService.removeCategory(10L, 1L)).isInstanceOf(InvalidBasicCategoryException.class).hasMessage("기본 카테고리는 변경이 불가합니다.");
    }
}
