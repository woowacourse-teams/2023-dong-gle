package org.donggle.backend.application.service;

import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.service.auth.MemberService;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.domain.member.MemberName;
import org.donggle.backend.ui.response.MemberPageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest
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
        final Member member = memberRepository.save(Member.of(new MemberName("토리"), 1234L));
        final MemberCredentials savedMemberCredentials = memberCredentialsRepository.save(MemberCredentials.basic(member));
        //when
        final MemberPageResponse memberPage = memberService.findMemberPage(member.getId());

        //then
        assertAll(
                () -> assertThat(memberPage.id()).isEqualTo(member.getId()),
                () -> assertThat(memberPage.name()).isEqualTo(member.getMemberName().getName()),
                () -> assertThat(memberPage.tistory().isConnected()).isFalse(),
                () -> assertThat(memberPage.tistory().blogName()).isNull(),
                () -> assertThat(memberPage.notion().isConnected()).isFalse(),
                () -> assertThat(memberPage.medium().isConnected()).isFalse()
        );
    }

    @Test
    @DisplayName("회원 페이지 조회 - 티스토리 연결된 회원")
    void findMemberPage2() {
        //given
        final Member member = memberRepository.findById(1L).get();
        final MemberCredentials savedMemberCredentials = memberCredentialsRepository.findById(1L).get();
        savedMemberCredentials.updateTistory("test", "test");
        //when
        final MemberPageResponse memberPage = memberService.findMemberPage(member.getId());

        //then
        assertAll(
                () -> assertThat(memberPage.id()).isEqualTo(member.getId()),
                () -> assertThat(memberPage.name()).isEqualTo(member.getMemberName().getName()),
                () -> assertThat(memberPage.tistory().isConnected()).isTrue(),
                () -> assertThat(memberPage.tistory().blogName()).isEqualTo("test"),
                () -> assertThat(memberPage.notion().isConnected()).isFalse(),
                () -> assertThat(memberPage.medium().isConnected()).isTrue()
        );
    }
}
