package org.donggle.backend.domain.writing;

import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberName;
import org.donggle.backend.domain.writing.block.Block;
import org.donggle.backend.domain.writing.block.CodeBlock;
import org.donggle.backend.domain.writing.block.Language;
import org.donggle.backend.domain.writing.block.RawText;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.donggle.backend.domain.oauth.SocialType.KAKAO;

//todo: 슬라이스 테스트로 바꾸기
@SpringBootTest
@Transactional
class BlockTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private WritingRepository writingRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("block과 content save 테스트")
    void blockSave() {
        //given
        final Member member = Member.of(new MemberName("동그리"), 3L, KAKAO);
        final Member savedMember = memberRepository.save(member);
        final Category basicCategory = categoryRepository.findById(1L).get();
        final Block codeBlock = new CodeBlock(BlockType.CODE_BLOCK, RawText.from("r"), Language.from("l"));
        final Writing writing = Writing.of(savedMember, new Title("title"), basicCategory, List.of(codeBlock));
        final Writing savedWriting = writingRepository.save(writing);

        //when
        final Writing findWriting = writingRepository.findById(savedWriting.getId()).get();
        final Block findBlock = findWriting.getBlocks().get(0);

        //then
        assertThat(findBlock).isEqualTo(codeBlock);
    }
}
