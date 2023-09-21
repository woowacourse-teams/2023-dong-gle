package org.donggle.backend.infrastructure.oauth.kakao.dto.response;

import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberName;
import org.donggle.backend.domain.oauth.SocialType;

public record UserInfo(
        Long socialId,
        SocialType socialType,
        String nickname
) {
    public Member toMember() {
        return Member.of(
                new MemberName(nickname),
                socialId
        );
    }
}