package org.donggle.backend.application.service;

import org.assertj.core.api.Assertions;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.ui.response.MemberPageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberCredentialsRepository memberCredentialsRepository;

    @Test
    @DisplayName("회원 페이지 조회 - 아무것도 연결되어있지 않은 회원")
    void findMemberPage() {
        //given
        final Member member = memberRepository.findById(1L).get();
        final MemberCredentials savedMemberCredentials = memberCredentialsRepository.save(MemberCredentials.basic(member));
        //when
        final MemberPageResponse memberPage = memberService.findMemberPage(member.getId());

        //then
        assertAll(
                () -> Assertions.assertThat(memberPage.id()).isEqualTo(member.getId()),
                () -> Assertions.assertThat(memberPage.name()).isEqualTo(member.getMemberName().getName()),
                () -> Assertions.assertThat(memberPage.tistoryConnection().isConnected()).isEqualTo(false),
                () -> Assertions.assertThat(memberPage.tistoryConnection().blogName()).isNull(),
                () -> Assertions.assertThat(memberPage.notionConnection().isConnected()).isEqualTo(false),
                () -> Assertions.assertThat(memberPage.mediumConnection().isConnected()).isEqualTo(false)
        );
    }

    @Test
    @DisplayName("회원 페이지 조회 - 티스토리 연결된 회원")
    void findMemberPage2() {
        //given
        final Member member = memberRepository.findById(1L).get();
        final MemberCredentials savedMemberCredentials = memberCredentialsRepository.save(MemberCredentials.basic(member));
        savedMemberCredentials.updateTistory("test", "test");
        //when
        final MemberPageResponse memberPage = memberService.findMemberPage(member.getId());

        //then
        assertAll(
                () -> Assertions.assertThat(memberPage.id()).isEqualTo(member.getId()),
                () -> Assertions.assertThat(memberPage.name()).isEqualTo(member.getMemberName().getName()),
                () -> Assertions.assertThat(memberPage.tistoryConnection().isConnected()).isEqualTo(true),
                () -> Assertions.assertThat(memberPage.tistoryConnection().blogName()).isEqualTo("test"),
                () -> Assertions.assertThat(memberPage.notionConnection().isConnected()).isEqualTo(false),
                () -> Assertions.assertThat(memberPage.mediumConnection().isConnected()).isEqualTo(false)
        );
    }
}