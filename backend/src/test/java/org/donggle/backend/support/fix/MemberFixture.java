package org.donggle.backend.support.fix;

import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberName;

import static org.donggle.backend.domain.oauth.SocialType.KAKAO;

public class MemberFixture {
    public static final Member beaver_have_id = new Member(10L, new MemberName("비버"), 100L, KAKAO);
    public static final Member beaver_have_not_id = Member.of(new MemberName("비버"), 100L, KAKAO);
}
