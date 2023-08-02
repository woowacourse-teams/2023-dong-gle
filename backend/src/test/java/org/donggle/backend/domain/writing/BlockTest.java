package org.donggle.backend.domain.writing;

import org.donggle.backend.application.repository.BlockRepository;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Email;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberName;
import org.donggle.backend.domain.member.Password;
import org.donggle.backend.domain.writing.content.CodeBlockContent;
import org.donggle.backend.domain.writing.content.Language;
import org.donggle.backend.domain.writing.content.RawText;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BlockTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private WritingRepository writingRepository;
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("block과 content save 테스트")
    void blockSave() {
        //given
        final Member member = new Member(new MemberName("동그리"), new Email("a@a.com"), new Password("1234"));
        final Member savedMember = memberRepository.save(member);
        Category basicCategory = categoryRepository.findById(1L).get();
        final Writing writing = new Writing(savedMember, new Title("title"), basicCategory);
        final Writing savedWriting = writingRepository.save(writing);
        final CodeBlockContent expectedContent = new CodeBlockContent(BlockType.CODE_BLOCK, RawText.from("r"), Language.from("l"));
        final Block block = new Block(savedWriting, expectedContent);

        //when
        final Block savedBlock = blockRepository.save(block);
        blockRepository.flush();

        //then
        final Block findBlock = blockRepository.findById(savedBlock.getId()).get();
        assertThat(findBlock.getContent()).isEqualTo(expectedContent);
    }
}
