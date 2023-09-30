package org.donggle.backend.application.repository;

import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.category.CategoryName;
import org.donggle.backend.domain.encryption.AESEncryptionUtil;
import org.donggle.backend.domain.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.donggle.backend.support.fix.MemberFixture.beaver_have_not_id;

@DataJpaTest
class CategoryRepositoryTest {
    @Autowired
    private WritingRepository writingRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MemberRepository memberRepository;
    @MockBean
    private AESEncryptionUtil aesEncryptionUtil;

    @Test
    @DisplayName("마지막 카테고리를 가져오는 테스트")
    void findLastCategoryByMemberId() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category2 = new Category(1L, new CategoryName("카테고리"), null, member);
        final Category saveCategory2 = categoryRepository.save(category2);
        final Category category1 = new Category(2L, new CategoryName("카테고리"), saveCategory2, member);
        final Category saveCategory1 = categoryRepository.save(category1);

        //when
        final Category findCategory = categoryRepository.findLastCategoryByMemberId(member.getId()).get();

        //then
        assertThat(findCategory).usingRecursiveAssertion().isEqualTo(saveCategory2);
    }

    @Test
    @DisplayName("사용자의 카테고리를 모두 가져오는 테스트")
    void findAllByMemberId() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category2 = new Category(1L, new CategoryName("카테고리"), null, member);
        final Category saveCategory2 = categoryRepository.save(category2);
        final Category category1 = new Category(2L, new CategoryName("카테고리"), saveCategory2, member);
        final Category saveCategory1 = categoryRepository.save(category1);

        //when
        final List<Category> categories = categoryRepository.findAllByMemberId(member.getId());

        //then
        assertThat(categories).hasSize(2);
    }

    @Test
    @DisplayName("맨 처음 카테고리를 가져오는 테스")
    void findFirstByMemberId() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category2 = new Category(2L, new CategoryName("카테고리"), null, member);
        final Category saveCategory2 = categoryRepository.save(category2);
        final Category category1 = new Category(1L, new CategoryName("카테고리"), saveCategory2, member);
        final Category saveCategory1 = categoryRepository.save(category1);

        //when
        final Category category = categoryRepository.findFirstByMemberId(member.getId()).get();

        //then
        assertThat(category).usingRecursiveAssertion().isEqualTo(saveCategory1);
    }

    @Test
    @DisplayName("카테고리의 이름이 중복이면 true")
    void existsByMemberIdAndCategoryName_duplicate() {
        //given
        final CategoryName categoryName = new CategoryName("카테고리");
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category = new Category(1L, categoryName, null, member);
        categoryRepository.save(category);

        //when
        //then
        assertThat(categoryRepository.existsByMemberIdAndCategoryName(member.getId(), categoryName)).isTrue();
    }

    @Test
    @DisplayName("카테고리의 이름이 중복이 아니면 false")
    void existsByMemberIdAndCategoryName_not_duplicate() {
        //given
        final CategoryName categoryName = new CategoryName("그래고리");
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category = new Category(1L, new CategoryName("카테고리"), null, member);
        categoryRepository.save(category);

        //when
        //then
        assertThat(categoryRepository.existsByMemberIdAndCategoryName(member.getId(), categoryName)).isFalse();
    }

    @Test
    @DisplayName("해당 카테고리의 이전 카테고리 조회 테스")
    void findPreCategoryByCategoryId() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category2 = new Category(1L, new CategoryName("카테고리"), null, member);
        final Category saveCategory2 = categoryRepository.save(category2);
        final Category category1 = new Category(2L, new CategoryName("카테고리"), saveCategory2, member);
        final Category saveCategory1 = categoryRepository.save(category1);

        //when
        final Category category = categoryRepository.findPreCategoryByCategoryId(category2.getId()).get();

        //then
        assertThat(category).usingRecursiveAssertion().isEqualTo(saveCategory1);
    }

    @Test
    @DisplayName("해당 사용자의 카테고리 조회 테스트")
    void findByIdAndMemberId() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category = new Category(1L, new CategoryName("카테고리"), null, member);
        final Category saveCategory = categoryRepository.save(category);

        //when
        final Category findCategory = categoryRepository.findByIdAndMemberId(saveCategory.getId(), member.getId()).get();

        //then
        assertThat(findCategory).usingRecursiveAssertion().isEqualTo(category);
    }
}
