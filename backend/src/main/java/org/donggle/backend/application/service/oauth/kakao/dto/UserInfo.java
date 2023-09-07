package org.donggle.backend.application.service.oauth.kakao.dto;

import org.donggle.backend.application.service.oauth.SocialType;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberName;

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