package org.donggle.backend.application.service.member;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.BlogWritingRepository;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.TokenRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.domain.blog.BlogWriting;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.domain.writing.Writing;
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
        final List<Writing> writings = writingRepository.findByMember(member);
        final List<BlogWriting> blogWritings = blogWritingRepository.findAllByWritingIds(writings);

        writingRepository.deleteAll(writings);
        blogWritingRepository.deleteAll(blogWritings);
    }

    private void deleteMemberCredentials(final Member member) {
        memberCredentialsRepository.findByMember(member)
                .ifPresent(memberCredentialsRepository::delete);
    }

    private void deleteCategories(final Member member) {
        final List<Category> categories = categoryRepository.findAllByMemberId(member.getId());
        categoryRepository.deleteAll(categories);
    }

    private void deleteToken(final Member member) {
        tokenRepository.findByMemberId(member.getId())
                .ifPresent(tokenRepository::delete);
    }

    private Member findMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }
}
