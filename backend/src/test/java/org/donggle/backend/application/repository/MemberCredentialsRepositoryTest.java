package org.donggle.backend.application.repository;

import org.donggle.backend.domain.encryption.AESEncryptionUtil;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.donggle.backend.support.fix.MemberFixture.beaver_have_not_id;

@DataJpaTest
class MemberCredentialsRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberCredentialsRepository memberCredentialsRepository;
    @MockBean
    private AESEncryptionUtil aesEncryptionUtil;

    @Test
    @DisplayName("사용자의 정보를 조회하는 테스트")
    void findMemberCredentialsByMember() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);
        final MemberCredentials memberCredentials = MemberCredentials.basic(member);
        memberCredentialsRepository.save(memberCredentials);

        //when
        final MemberCredentials findMemberCredentials = memberCredentialsRepository.findByMember(member).get();

        //then
        assertThat(findMemberCredentials).usingRecursiveAssertion().ignoringFields("id").isEqualTo(memberCredentials);
    }
}
