package org.donggle.backend.application.repository;

import org.donggle.backend.domain.auth.RefreshToken;
import org.donggle.backend.domain.encryption.AESEncryptionUtil;
import org.donggle.backend.domain.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.donggle.backend.support.fix.MemberFixture.beaver_have_not_id;

@DataJpaTest
class TokenRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @MockBean
    private AESEncryptionUtil aesEncryptionUtil;

    @Test
    @DisplayName("사용자의 RefreshToken을 조회하는 테스트")
    void findByMemberId() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);
        final RefreshToken refreshToken = new RefreshToken("token", member);
        final RefreshToken saveToken = tokenRepository.save(refreshToken);

        //when
        final RefreshToken findRefreshToken = tokenRepository.findByMemberId(member.getId()).get();

        //then
        assertThat(findRefreshToken).usingRecursiveAssertion().isEqualTo(saveToken);
    }

    @Test
    @DisplayName("사용자의 RefreshToken을 제거하는 테스트")
    void deleteByMemberId() {
        //given
        final Member member = memberRepository.save(beaver_have_not_id);
        final RefreshToken refreshToken = new RefreshToken("token", member);
        final RefreshToken saveToken = tokenRepository.save(refreshToken);

        //when
        tokenRepository.deleteByMemberId(member.getId());

        //then
        assertThat(tokenRepository.findByMemberId(member.getId()).isPresent()).isFalse();
    }
}