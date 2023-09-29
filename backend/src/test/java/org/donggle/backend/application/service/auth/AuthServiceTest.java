package org.donggle.backend.application.service.auth;

import org.assertj.core.api.Assertions;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.TokenRepository;
import org.donggle.backend.domain.auth.JwtTokenProvider;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.oauth.SocialType;
import org.donggle.backend.exception.business.DuplicatedMemberException;
import org.donggle.backend.infrastructure.oauth.kakao.dto.response.UserInfo;
import org.donggle.backend.ui.response.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.donggle.backend.domain.oauth.SocialType.KAKAO;
import static org.donggle.backend.support.fix.MemberFixture.beaver;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private MemberCredentialsRepository memberCredentialsRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("로그인 테스트")
    void login() {
        // given
        final Long memberId = 10L;
        final SocialType socialType = KAKAO;
        final UserInfo userInfo = new UserInfo(1L, KAKAO, "비버");

        given(memberRepository.findBySocialIdAndSocialType(1L, KAKAO))
                .willReturn(Optional.of(beaver));
        given(jwtTokenProvider.createAccessToken(memberId))
                .willReturn("accessToken");
        given(jwtTokenProvider.createRefreshToken(memberId))
                .willReturn("refreshToken");
        // when
        final TokenResponse result = authService.login(userInfo, socialType);

        // then
        assertThat(result).isNotNull();
        assertThat(result.accessToken()).isEqualTo("accessToken");
        assertThat(result.refreshToken()).isEqualTo("refreshToken");
    }

    @Test
    @DisplayName("회원가입 테스트")
    void registry() {
        // given
        final Long memberId = 10L;
        final long socialId = 1L;
        final SocialType socialType = KAKAO;
        final UserInfo userInfo = new UserInfo(socialId, KAKAO, "비버");

        given(memberRepository.findBySocialIdAndSocialType(socialId, KAKAO))
                .willReturn(Optional.empty());
        given(memberRepository.save(any(Member.class))).willReturn(beaver);
        given(jwtTokenProvider.createAccessToken(memberId))
                .willReturn("accessToken");
        given(jwtTokenProvider.createRefreshToken(memberId))
                .willReturn("refreshToken");

        // when
        // then
        final TokenResponse result = authService.login(userInfo, socialType);
        assertThat(result).isNotNull();
        assertThat(result.accessToken()).isEqualTo("accessToken");
        assertThat(result.refreshToken()).isEqualTo("refreshToken");
    }

    @Test
    @DisplayName("중복 회원가입 시 에러")
    void registry_DuplicatedMember() {
        // given
        final long socialId = 1L;
        final SocialType socialType = KAKAO;
        final UserInfo userInfo = new UserInfo(socialId, KAKAO, "비버");

        given(memberRepository.findBySocialIdAndSocialType(socialId, KAKAO))
                .willReturn(Optional.empty());
        given(memberRepository.save(any(Member.class))).willThrow(new DuplicatedMemberException("비버"));

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> authService.login(userInfo, socialType)
        ).isInstanceOf(DuplicatedMemberException.class);
    }

    @Test
    @DisplayName("토큰 재발급 테스트")
    void testReissueAccessTokenAndRefreshToken() {
        // given
        final Long memberId = 10L;

        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver));
        given(jwtTokenProvider.createAccessToken(memberId)).willReturn("newAccessToken");
        given(jwtTokenProvider.createRefreshToken(memberId)).willReturn("newRefreshToken");

        // when
        final TokenResponse result = authService.reissueAccessTokenAndRefreshToken(memberId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.accessToken()).isEqualTo("newAccessToken");
        assertThat(result.refreshToken()).isEqualTo("newRefreshToken");
    }
}