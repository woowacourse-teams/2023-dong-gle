package org.donggle.backend.fix;

import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberName;

import static org.donggle.backend.domain.oauth.SocialType.KAKAO;

public class MemberFixture {
    public static final Member beaver = new Member(10L, new MemberName("비버"), 1L, KAKAO);
}
