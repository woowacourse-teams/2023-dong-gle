package org.donggle.backend.application.service.connection.medium;

import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.service.request.AddTokenRequest;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class MediumConnectionService {
    private final MemberRepository memberRepository;
    private final MemberCredentialsRepository memberCredentialsRepository;

    public MediumConnectionService(
            final MemberRepository memberRepository,
            final MemberCredentialsRepository memberCredentialsRepository
    ) {
        this.memberRepository = memberRepository;
        this.memberCredentialsRepository = memberCredentialsRepository;
    }

    public void saveAccessToken(final Long memberId, final AddTokenRequest addTokenRequest) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        final String accessToken = addTokenRequest.token();

        final MemberCredentials memberCredentials = memberCredentialsRepository.findMemberCredentialsByMember(member)
                .orElseThrow(NoSuchElementException::new);

        memberCredentials.updateMediumToken(accessToken);
    }

    public void deleteAccessToken(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        final MemberCredentials memberCredentials = memberCredentialsRepository.findMemberCredentialsByMember(member)
                .orElseThrow(NoSuchElementException::new);

        memberCredentials.deleteMediumConnection();
    }
}
