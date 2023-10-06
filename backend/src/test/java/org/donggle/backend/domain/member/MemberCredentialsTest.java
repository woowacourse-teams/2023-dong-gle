package org.donggle.backend.domain.member;

import jakarta.persistence.EntityManager;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.support.fix.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

//todo: 슬라이스 테스트로 바꾸기
@SpringBootTest
@Transactional
class MemberCredentialsTest {
    @Autowired
    private MemberCredentialsRepository memberCredentialsRepository;
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
        memberCredentialsRepository.flush();
        entityManager.clear();

        //then
        final String notionToken = memberCredentialsRepository
                .findById(memberCredentials.getId())
                .get()
                .getNotionToken()
                .get();
        assertThat(notionToken).isEqualTo("test");
    }

    @Test
    @DisplayName("token을 삭제하기_tistory")
    void deleteTistoryConnectionTest() {
        //given
        final MemberCredentials basic = MemberCredentials.basic(MemberFixture.beaver_have_id);
        basic.updateTistory("123", "jeoninpyo726");

        //when
        basic.deleteTistoryConnection();
        //then
        assertThat(basic.getTistoryBlogName()).isEmpty();
        assertThat(basic.getTistoryToken()).isEmpty();
    }

    @Test
    @DisplayName("token을 삭제하기_medium")
    void deleteMediumConnectionTest() {
        //given
        final MemberCredentials basic = MemberCredentials.basic(MemberFixture.beaver_have_id);
        basic.updateMediumToken("jeoninpyo726");

        //when
        basic.deleteMediumConnection();
        //then
        assertThat(basic.getMediumToken()).isEmpty();
    }

    @Test
    @DisplayName("token을 삭제하기_notion")
    void deleteNotionConnectionTest() {
        //given
        final MemberCredentials basic = MemberCredentials.basic(MemberFixture.beaver_have_id);
        basic.updateNotionToken("jeoninpyo726");

        //when
        basic.deleteNotionConnection();
        //then
        assertThat(basic.getNotionToken()).isEmpty();
    }
}