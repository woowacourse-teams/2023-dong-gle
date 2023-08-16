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

    public MemberPageResponse findMemberPage(final Long memberId) {
        final Member foundMember = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));
        final MemberCredentials foundMemberCredentials = memberCredentialsRepository.findMemberCredentialsByMember(foundMember).orElseThrow(NoSuchElementException::new);
        return MemberPageResponse.from(foundMember, foundMemberCredentials);
    }
}
