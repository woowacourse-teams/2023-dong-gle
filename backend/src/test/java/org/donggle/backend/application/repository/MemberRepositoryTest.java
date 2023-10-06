package org.donggle.backend.application.repository;

import jakarta.persistence.EntityManager;
import org.donggle.backend.application.repository.dto.MemberInfo;
import org.donggle.backend.domain.encryption.AESEncryptionUtil;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.donggle.backend.domain.oauth.SocialType.KAKAO;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager em;
    @MockBean
    private AESEncryptionUtil aesEncryptionUtil;

    @BeforeEach
    void setUp() {
        // given
        memberRepository.saveAll(List.of(
                Member.of(new MemberName("member1"), 11L, KAKAO),
                Member.of(new MemberName("member2"), 12L, KAKAO),
                Member.of(new MemberName("member3"), 13L, KAKAO)
        ));
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("existsById 쿼리 확인")
    void existsByIdTest() {
        // when
        final boolean isExist = memberRepository.existsById(1L);
        em.flush();

        // then
        assertThat(isExist).isFalse();
    }

    @Test
    @DisplayName("findBySocialIdAndSocialType 쿼리 확인")
    void findBySocialIdAndSocialTypeTest() {
        //when
        final Optional<MemberInfo> memberInfo = memberRepository.findBySocialIdAndSocialType(11L, KAKAO);

        //then
        assertAll(
                () -> assertThat(memberInfo).isNotEmpty(),
                () -> assertThat(memberInfo.get().id()).isNotNull(),
                () -> assertThat(memberInfo.get().socialId()).isNotNull()
        );
    }
}
