package org.donggle.backend.ui.response;

import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;

public record MemberPageResponse(
        Long id,
        String name,
        TistoryConnectionResponse tistory,
        NotionConnectionResponse notion,
        MediumConnectionResponse medium
) {
    public static MemberPageResponse of(final Member member, final MemberCredentials memberCredentials) {
        return new MemberPageResponse(
                member.getId(),
                member.getMemberName().getName(),
                new TistoryConnectionResponse(memberCredentials.isTistoryConnected(), memberCredentials.getTistoryBlogName().orElse(null)),
                new NotionConnectionResponse(memberCredentials.isNotionConnected()),
                new MediumConnectionResponse(memberCredentials.isMediumConnected())
        );
    }
}
