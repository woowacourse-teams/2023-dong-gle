package org.donggle.backend.application.service.connection.medium;

import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.service.request.AddTokenRequest;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.springframework.stereotype.Service;

@Service
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

        final MemberCredentials findMemberCredentials = memberCredentialsRepository.findMemberCredentialsByMember(member)
                .map(memberCredentials -> memberCredentials.updateMediumToken(accessToken))
                .orElseGet(() -> creatMemberCredentials(member, accessToken));

        memberCredentialsRepository.save(findMemberCredentials);
    }

    private MemberCredentials creatMemberCredentials(final Member member, final String accessToken) {
        return MemberCredentials.createByMediumToken(member, accessToken);
    }
}
