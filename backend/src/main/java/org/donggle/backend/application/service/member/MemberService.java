package org.donggle.backend.application.service.member;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.BlockRepository;
import org.donggle.backend.application.repository.BlogWritingRepository;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.StyleRepository;
import org.donggle.backend.application.repository.TokenRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.block.Block;
import org.donggle.backend.domain.writing.block.NormalBlock;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.donggle.backend.ui.response.MemberPageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberCredentialsRepository memberCredentialsRepository;
    private final WritingRepository writingRepository;
    private final BlockRepository blockRepository;
    private final StyleRepository styleRepository;
    private final BlogWritingRepository blogWritingRepository;
    private final CategoryRepository categoryRepository;
    private final TokenRepository tokenRepository;

    @Transactional(readOnly = true)
    public MemberPageResponse findMemberPage(final Long memberId) {
        final Member member = findMember(memberId);
        final MemberCredentials memberCredentials = memberCredentialsRepository.findByMember(member)
                .orElseThrow(NoSuchElementException::new);
        return MemberPageResponse.of(member, memberCredentials);
    }

    public void deleteMember(final Long memberId) {
        final Member member = findMember(memberId);
        deleteWritings(member);
        deleteCategories(member);
        deleteToken(member);
        deleteMemberCredentials(member);
        memberRepository.delete(member);
    }

    private void deleteWritings(final Member member) {
        final List<Writing> writings = writingRepository.findAllByMember(member);
        final List<Long> memberIds = writings.stream()
                .map(writing -> writing.getMember().getId())
                .toList();

        for (final Writing writing : writings) {
            final List<Block> blocks = writing.getBlocks();
            final List<Long> totalBlockIds = blocks.stream()
                    .map(Block::getId)
                    .toList();

            final List<NormalBlock> normalBlocks = blockRepository.findNormalBlocksByIds(totalBlockIds);
            for (final NormalBlock normalBlock : normalBlocks) {
                final List<Long> styleIds = normalBlock.getStyles().stream()
                        .map(Style::getId)
                        .toList();
                styleRepository.deleteAllByIds(styleIds);
            }
            blockRepository.deleteAllByIds(totalBlockIds);
        }

        writingRepository.deleteAllByMember(memberIds);
        blogWritingRepository.deleteAllByWritings(writings);
    }

    private void deleteCategories(final Member member) {
        categoryRepository.deleteAllByMember(member);
    }

    private void deleteMemberCredentials(final Member member) {
        memberCredentialsRepository.deleteByMember(member);
    }

    private void deleteToken(final Member member) {
        tokenRepository.deleteByMemberId(member.getId());
    }

    private Member findMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }
}
