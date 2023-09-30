package org.donggle.backend.application.service;

import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.service.member.MemberService;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.donggle.backend.ui.response.MemberPageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.donggle.backend.support.fix.MemberFixture.beaver_have_id;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberCredentialsRepository memberCredentialsRepository;


    @Test
    @DisplayName("회원 페이지 조회 시 사용자를 찾을 수 없을때 에러")
    void findMemberPage_member_not_found() {
        //given
        final long memberId = 10L;

        given(memberRepository.findById(memberId)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(
                () -> memberService.findMemberPage(memberId)
        ).isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("회원 페이지 조회 - 아무것도 연결되어있지 않은 회원")
    void findMemberPage_empty() {
        //given
        final long memberId = 10L;
        final MemberCredentials basic = MemberCredentials.basic(beaver_have_id);
        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver_have_id));
        given(memberCredentialsRepository.findMemberCredentialsByMember(beaver_have_id)).willReturn(Optional.of(basic));
        //when
        final MemberPageResponse memberPage = memberService.findMemberPage(memberId);
        //then
        assertAll(
                () -> assertThat(memberPage.id()).isEqualTo(memberId),
                () -> assertThat(memberPage.name()).isEqualTo(beaver_have_id.getMemberName().getName()),
                () -> assertThat(memberPage.tistory().isConnected()).isFalse(),
                () -> assertThat(memberPage.notion().isConnected()).isFalse(),
                () -> assertThat(memberPage.medium().isConnected()).isFalse()
        );
    }

    @Test
    @DisplayName("회원 페이지 조회 - 노션 연결된 회원")
    void findMemberPage_notion() {
        //given
        final long memberId = 10L;
        final MemberCredentials basic = MemberCredentials.basic(beaver_have_id);
        basic.updateNotionToken("token");
        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver_have_id));
        given(memberCredentialsRepository.findMemberCredentialsByMember(beaver_have_id)).willReturn(Optional.of(basic));
        //when
        final MemberPageResponse memberPage = memberService.findMemberPage(memberId);

        //then
        assertAll(
                () -> assertThat(memberPage.id()).isEqualTo(memberId),
                () -> assertThat(memberPage.name()).isEqualTo(beaver_have_id.getMemberName().getName()),
                () -> assertThat(memberPage.tistory().isConnected()).isFalse(),
                () -> assertThat(memberPage.notion().isConnected()).isTrue(),
                () -> assertThat(memberPage.medium().isConnected()).isFalse()
        );
    }

    @Test
    @DisplayName("회원 페이지 조회 - 티스토리 연결된 회원")
    void findMemberPage_tistory() {
        //given
        final long memberId = 10L;
        final MemberCredentials basic = MemberCredentials.basic(beaver_have_id);
        basic.updateTistory("token", "jeoninpyo");
        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver_have_id));
        given(memberCredentialsRepository.findMemberCredentialsByMember(beaver_have_id)).willReturn(Optional.of(basic));
        //when
        final MemberPageResponse memberPage = memberService.findMemberPage(memberId);

        //then
        assertAll(
                () -> assertThat(memberPage.id()).isEqualTo(memberId),
                () -> assertThat(memberPage.name()).isEqualTo(beaver_have_id.getMemberName().getName()),
                () -> assertThat(memberPage.tistory().blogName()).isEqualTo("jeoninpyo"),
                () -> assertThat(memberPage.tistory().isConnected()).isTrue(),
                () -> assertThat(memberPage.notion().isConnected()).isFalse(),
                () -> assertThat(memberPage.medium().isConnected()).isFalse()
        );
    }

    @Test
    @DisplayName("회원 페이지 조회 - 미디엄 연결된 회원")
    void findMemberPage_medium() {
        //given
        final long memberId = 10L;
        final MemberCredentials basic = MemberCredentials.basic(beaver_have_id);
        basic.updateMediumToken("token");
        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver_have_id));
        given(memberCredentialsRepository.findMemberCredentialsByMember(beaver_have_id)).willReturn(Optional.of(basic));
        //when
        final MemberPageResponse memberPage = memberService.findMemberPage(memberId);

        //then
        assertAll(
                () -> assertThat(memberPage.id()).isEqualTo(memberId),
                () -> assertThat(memberPage.name()).isEqualTo(beaver_have_id.getMemberName().getName()),
                () -> assertThat(memberPage.tistory().isConnected()).isFalse(),
                () -> assertThat(memberPage.notion().isConnected()).isFalse(),
                () -> assertThat(memberPage.medium().isConnected()).isTrue()
        );
    }

    @Test
    @DisplayName("회원을 탈퇴한다.")
    void deleteMember() {
        //given
        final long memberId = 10L;
        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver_have_id));

        //when
        memberService.deleteMember(memberId);

        //then
        then(memberRepository).should(times(1)).delete(beaver_have_id);
    }
}
