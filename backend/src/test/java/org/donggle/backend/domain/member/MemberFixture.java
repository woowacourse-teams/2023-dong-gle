package org.donggle.backend.domain.member;

import static org.donggle.backend.domain.oauth.SocialType.KAKAO;

public class MemberFixture {
    public static final Member beaver = Member.of(new MemberName("비버"), 1L, KAKAO);
}
