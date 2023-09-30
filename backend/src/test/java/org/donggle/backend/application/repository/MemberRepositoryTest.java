package org.donggle.backend.application.repository;

import org.assertj.core.api.Assertions;
import org.donggle.backend.domain.encryption.AESEncryptionUtil;
import org.donggle.backend.domain.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.donggle.backend.domain.oauth.SocialType.KAKAO;
import static org.donggle.backend.support.fix.MemberFixture.beaver_have_not_id;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @MockBean
    private AESEncryptionUtil aesEncryptionUtil;

    @Test
    @DisplayName("로그인한 플랫폼과 플랫폼 id로 사용자 조회 테스트")
    void findBySocialIdAndSocialType() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);

        //when
        final Member findMember = memberRepository.findBySocialIdAndSocialType(100L, KAKAO).get();

        //then
        Assertions.assertThat(findMember).usingRecursiveAssertion().isEqualTo(member);
    }
}