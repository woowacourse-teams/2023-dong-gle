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
import org.donggle.backend.domain.category.Category;
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
        final List<Writing> writings = writingRepository.findAllByMember(member);
        deleteBlogWritings(writings);
        deleteStyles(writings);
        deleteWritings(writings);
        deleteCategories(member);
        deleteToken(member);
        deleteMemberCredentials(member);
        deleteMember(member);
    }

    private void deleteBlogWritings(final List<Writing> writings) {
        blogWritingRepository.deleteAllByWritings(writings);
    }

    private void deleteStyles(final List<Writing> writings) {
        final List<Long> blockIds = writings.stream()
                .flatMap(writing -> writing.getBlocks().stream().map(Block::getId))
                .toList();

        final List<NormalBlock> normalBlocks = blockRepository.findNormalBlocks(blockIds);

        final List<Long> styleIds = normalBlocks.stream()
                .flatMap(block -> block.getStyles().stream().map(Style::getId))
                .toList();

        styleRepository.deleteAllById(styleIds);
    }

    private void deleteWritings(final List<Writing> writings) {
        final List<Writing> nextWritings = writings.stream()
                .map(Writing::getNextWriting)
                .toList();
        writings.removeAll(nextWritings);
        writings.forEach(writing -> {
            Writing target = writing;
            while (target != null) {
                blockRepository.deleteAll(target.getBlocks());
                writingRepository.deleteImmediately(target);
                target = target.getNextWriting();
            }
        });
    }

    private void deleteCategories(final Member member) {
        final List<Category> categories = categoryRepository.findAllByMemberId(member.getId());
        Category target = categories.get(0);
        while (target != null) {
            categoryRepository.delete(target);
            target = target.getNextCategory();
        }
    }

    private void deleteToken(final Member member) {
        tokenRepository.deleteByMember(member);
    }

    private void deleteMemberCredentials(final Member member) {
        memberCredentialsRepository.deleteByMember(member);
    }

    private void deleteMember(final Member member) {
        memberRepository.delete(member);
    }

    private Member findMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }
}
