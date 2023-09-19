package org.donggle.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.donggle.backend.ui.response.MemberPageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberCredentialsRepository memberCredentialsRepository;

    @Transactional(readOnly = true)
    public MemberPageResponse findMemberPage(final Long memberId) {
        final Member member = findMember(memberId);
        final MemberCredentials memberCredentials = memberCredentialsRepository.findMemberCredentialsByMember(member)
                .orElseThrow(NoSuchElementException::new);
        return MemberPageResponse.of(member, memberCredentials);
    }

    public void deleteMember(final Long memberId) {
        final Member member = findMember(memberId);
        memberRepository.delete(member);
    }

    private Member findMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }
}
