package org.donggle.backend.application.repository;

import org.assertj.core.api.AssertionsForClassTypes;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.category.CategoryName;
import org.donggle.backend.domain.encryption.AESEncryptionUtil;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleRange;
import org.donggle.backend.domain.writing.StyleType;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.block.Depth;
import org.donggle.backend.domain.writing.block.NormalBlock;
import org.donggle.backend.domain.writing.block.RawText;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.donggle.backend.domain.writing.WritingStatus.ACTIVE;
import static org.donggle.backend.domain.writing.WritingStatus.DELETED;
import static org.donggle.backend.domain.writing.WritingStatus.TRASHED;
import static org.donggle.backend.support.fix.MemberFixture.beaver_have_not_id;

@DataJpaTest
class WritingRepositoryTest {
    @Autowired
    private WritingRepository writingRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @MockBean
    private AESEncryptionUtil aesEncryptionUtil;


    @Test
    @DisplayName("영구삭제한을 제외한 글의 본문 내용과 함께 조회 테스트")
    void findByWithBlocks_not_DELETED() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category = new Category(new CategoryName("안녕"), null, member);
        final Category saveCategory = categoryRepository.save(category);
        final Writing writing = Writing.of(member, new Title("Title 1"), saveCategory, List.of(new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of())));
        final Writing saveWriting = writingRepository.save(writing);

        //when
        final Writing findWriting = writingRepository.findByWithBlocks(saveWriting.getId(), member.getId()).get();

        //then
        AssertionsForClassTypes.assertThat(findWriting.getBlocks()).usingRecursiveAssertion().isEqualTo(writing.getBlocks());
    }

    @Test
    @DisplayName("사용자의 글ID에 해당하는 글 조회 테스트")
    void findByIdAndMemberId() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category = new Category(new CategoryName("안녕"), null, member);
        final Category saveCategory = categoryRepository.save(category);
        final Writing writing = Writing.of(member, new Title("Title 1"), saveCategory, List.of(new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of())));
        final Writing saveWriting = writingRepository.save(writing);

        //when
        final Writing findWriting = writingRepository.findByWithBlocks(saveWriting.getId(), member.getId()).get();

        //then
        assertThat(findWriting.getTitleValue()).isEqualTo("Title 1");
    }

    @Test
    @DisplayName("WritingStatuses가 일치하는 글 가져오는 테스트")
    void findAllByMemberIdAndCategoryIdInStatuses() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category = new Category(new CategoryName("안녕"), null, member);
        final Category saveCategory = categoryRepository.save(category);
        final Writing writing_DELETED = new Writing(member, new Title("Title 1"), saveCategory, List.of(new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of())), null, DELETED);
        final Writing writing_ACTIVE = new Writing(member, new Title("Title 1"), saveCategory, List.of(new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of())), writing_DELETED, ACTIVE);
        final Writing writing_TRASHED = new Writing(member, new Title("Title 1"), saveCategory, List.of(new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of())), writing_ACTIVE, TRASHED);

        final Writing saveWriting_DELETED = writingRepository.save(writing_DELETED);
        final Writing saveWriting_ACTIVE = writingRepository.save(writing_ACTIVE);
        final Writing saveWriting_TRASHED = writingRepository.save(writing_TRASHED);

        //when
        final List<Writing> writings = writingRepository.findAllByMemberIdAndCategoryIdInStatuses(
                member.getId(),
                saveCategory.getId(),
                List.of(ACTIVE, TRASHED));

        //then
        assertThat(writings).hasSize(2);
        assertThat(writings).usingRecursiveAssertion().isEqualTo(List.of(saveWriting_ACTIVE, saveWriting_TRASHED));
    }

    @Test
    @DisplayName("WritingStatus가 일치하는 글 가져오는 테스트")
    void findAllByCategoryIdAndStatus() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category = new Category(new CategoryName("안녕"), null, member);
        final Category saveCategory = categoryRepository.save(category);
        final Writing writing_DELETED = new Writing(member, new Title("Title 1"), saveCategory, List.of(new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of())), null, DELETED);
        final Writing writing_ACTIVE1 = new Writing(member, new Title("Title 1"), saveCategory, List.of(new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of())), writing_DELETED, ACTIVE);
        final Writing writing_ACTIVE2 = new Writing(member, new Title("Title 1"), saveCategory, List.of(new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of())), writing_ACTIVE1, ACTIVE);

        final Writing saveWriting_DELETED = writingRepository.save(writing_DELETED);
        final Writing saveWriting_ACTIVE1 = writingRepository.save(writing_ACTIVE1);
        final Writing saveWriting_ACTIVE2 = writingRepository.save(writing_ACTIVE2);

        //when
        final List<Writing> writings = writingRepository.findAllByCategoryIdAndStatus(saveCategory.getId(), ACTIVE);

        //then
        assertThat(writings).hasSize(2);
        assertThat(writings).usingRecursiveAssertion().isEqualTo(List.of(saveWriting_ACTIVE1, saveWriting_ACTIVE2));
    }

    @Test
    @DisplayName("휴지통에 있는 글들을 가져오는 테스트")
    void findAllByMemberIdAndStatusIsTrashed() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category = new Category(new CategoryName("안녕"), null, member);
        final Category saveCategory = categoryRepository.save(category);
        final Writing writing_DELETED = new Writing(member, new Title("Title 1"), saveCategory, List.of(new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of())), null, DELETED);
        final Writing writing_TRASHED = new Writing(member, new Title("Title 1"), saveCategory, List.of(new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of())), writing_DELETED, TRASHED);

        final Writing saveWriting_DELETED = writingRepository.save(writing_DELETED);
        final Writing saveWriting_TRASHED = writingRepository.save(writing_TRASHED);

        //when
        final List<Writing> writings = writingRepository.findAllByMemberIdAndStatusIsTrashed(member.getId());

        //then
        assertThat(writings).hasSize(1);
        assertThat(writings.get(0)).usingRecursiveAssertion().isEqualTo(saveWriting_TRASHED);
    }

    @Test
    @DisplayName("ACTIVE인 글을 가져오는 테스트")
    void findByIdWithBlocks_ACTIVE() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category = new Category(new CategoryName("안녕"), null, member);
        final Category saveCategory = categoryRepository.save(category);
        final Writing writing = new Writing(member, new Title("Title 1"), saveCategory, List.of(new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of())), null, ACTIVE);
        final Writing saveWriting = writingRepository.save(writing);

        //when
        final Writing findWriting = writingRepository.findByIdWithBlocks(saveWriting.getId()).get();

        //then
        assertThat(findWriting).usingRecursiveAssertion().isEqualTo(saveWriting);
    }

    @Test
    @DisplayName("NormalBlocks의 스타일을 전부 찾아오는 테스트")
    void findStylesForBlocks() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category = new Category(new CategoryName("안녕"), null, member);
        final Category saveCategory = categoryRepository.save(category);
        final Writing writing_ACTIVE = new Writing(member, new Title("Title 1"), saveCategory,
                List.of(new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of(new Style(new StyleRange(0, 1), StyleType.BOLD))),
                        new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of(new Style(new StyleRange(0, 1), StyleType.CODE)))),
                null, ACTIVE);

        final Writing saveWriting_ACTIVE = writingRepository.save(writing_ACTIVE);
        final List<NormalBlock> blocks = saveWriting_ACTIVE.getBlocks().stream()
                .map(block -> (NormalBlock) block)
                .toList();
        //when
        final List<NormalBlock> normalBlocks = writingRepository.findStylesForBlocks(blocks);

        //then
        assertThat(normalBlocks).hasSize(2);
        assertThat(normalBlocks).usingRecursiveAssertion().isEqualTo(blocks);
    }

    @Test
    @DisplayName("해당 카테고리에 첫번째 글을 가져오는 테스트")
    void findLastWritingByCategoryId() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category = new Category(new CategoryName("안녕"), null, member);
        final Category saveCategory = categoryRepository.save(category);
        final Writing writing_ACTIVE1 = new Writing(member, new Title("Title 1"), saveCategory, List.of(new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of())), null, ACTIVE);
        final Writing writing_ACTIVE2 = new Writing(member, new Title("Title 1"), saveCategory, List.of(new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of())), writing_ACTIVE1, ACTIVE);
        final Writing writing_ACTIVE3 = new Writing(member, new Title("Title 1"), saveCategory, List.of(new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of())), writing_ACTIVE2, ACTIVE);

        final Writing saveWriting_ACTIVE1 = writingRepository.save(writing_ACTIVE1);
        final Writing saveWriting_ACTIVE2 = writingRepository.save(writing_ACTIVE2);
        final Writing saveWriting_ACTIVE3 = writingRepository.save(writing_ACTIVE3);

        //when
        final Writing findWriting = writingRepository.findLastWritingByCategoryId(saveCategory.getId()).get();

        //then
        assertThat(findWriting).usingRecursiveAssertion().isEqualTo(saveWriting_ACTIVE1);
    }

    @Test
    @DisplayName("해당 카테고리의 이전 카테고리를 가져오는 테스트")
    void findPreWritingByWritingId() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category = new Category(new CategoryName("안녕"), null, member);
        final Category saveCategory = categoryRepository.save(category);
        final Writing writing_ACTIVE1 = new Writing(member, new Title("Title 1"), saveCategory, List.of(new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of())), null, ACTIVE);
        final Writing writing_ACTIVE2 = new Writing(member, new Title("Title 1"), saveCategory, List.of(new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of())), writing_ACTIVE1, ACTIVE);
        final Writing writing_ACTIVE3 = new Writing(member, new Title("Title 1"), saveCategory, List.of(new NormalBlock(Depth.from(1), BlockType.PARAGRAPH, RawText.from("안녕"), List.of())), writing_ACTIVE2, ACTIVE);

        final Writing saveWriting_ACTIVE1 = writingRepository.save(writing_ACTIVE1);
        final Writing saveWriting_ACTIVE2 = writingRepository.save(writing_ACTIVE2);
        final Writing saveWriting_ACTIVE3 = writingRepository.save(writing_ACTIVE3);

        //when
        final Writing findWriting = writingRepository.findPreWritingByWritingId(writing_ACTIVE2.getId()).get();

        //then
        assertThat(findWriting).usingRecursiveAssertion().isEqualTo(saveWriting_ACTIVE3);
    }

    @Test
    @DisplayName("글의 상태와 id가 일치하면 글을 가져오는 테스트")
    void findByIdAndStatus() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);
        final Category category = categoryRepository.save(new Category(new CategoryName("Test Category"), null, member));
        final Writing writing = writingRepository.save(new Writing(member, new Title("Title 1"), category, List.of(), null, ACTIVE));

        //when
        final Writing findWriting = writingRepository.findByIdAndStatus(writing.getId(), ACTIVE).get();

        //then
        assertThat(findWriting).isEqualTo(writing);
    }
}