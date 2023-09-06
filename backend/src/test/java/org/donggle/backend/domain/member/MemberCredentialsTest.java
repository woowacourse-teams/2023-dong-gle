package org.donggle.backend.domain.member;

import jakarta.persistence.EntityManager;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberCredentialsTest {
    @Autowired
    private MemberCredentialsRepository memberCredentialsRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("AccessToken 암호화 테스트")
    void encryptAccessToken() {
        //given
        final MemberCredentials memberCredentials = memberCredentialsRepository.save(MemberCredentials.basic(null));

        //when
        memberCredentials.updateMediumToken("test");

        //then
        final String results = (String) entityManager
                .createNativeQuery("SELECT medium_token FROM member_credentials WHERE id = " + memberCredentials.getId())
                .getResultList().get(0);
        assertThat(results).isNotEqualTo("test");
    }

    @Test
    @DisplayName("AccessToken 복호화 테스트")
    void decryptAccessToken() {
        //given
        final MemberCredentials memberCredentials = memberCredentialsRepository.save(MemberCredentials.basic(null));


        //when
        memberCredentials.updateNotionToken("test");

        //then
        final String notionToken = memberCredentialsRepository
                .findById(memberCredentials.getId())
                .get()
                .getNotionToken()
                .get();
        assertThat(notionToken).isEqualTo("test");
    }
}