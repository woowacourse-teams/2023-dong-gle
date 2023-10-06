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
import org.donggle.backend.application.repository.dto.MemberInfo;
import org.donggle.backend.domain.auth.JwtTokenProvider;
import org.donggle.backend.domain.auth.RefreshToken;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.oauth.SocialType;
import org.donggle.backend.exception.authentication.ExpiredAccessTokenException;
import org.donggle.backend.exception.authentication.InvalidRefreshTokenException;
import org.donggle.backend.infrastructure.oauth.kakao.dto.response.UserInfo;
import org.donggle.backend.ui.response.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.donggle.backend.domain.oauth.SocialType.KAKAO;
import static org.donggle.backend.support.fix.MemberFixture.beaver_have_id;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.donggle.backend.domain.oauth.SocialType.KAKAO;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private MemberCredentialsRepository memberCredentialsRepository;
    @Mock
    private TokenRepository tokenRepository;

    @Nested
    class LoginTest {
        @Test
        @DisplayName("기존에 회원 정보가 없는 경우, 회원 정보를 새롭게 저장한다.")
        void findBySocialIdAndSocialTypeReturnEmpty() {
            //given
            given(memberRepository.findBySocialIdAndSocialType(anyLong(), any(SocialType.class))).willReturn(Optional.empty());

            //when
            final TokenResponse response = authService.login(new UserInfo(1234L, KAKAO, "nickname"), KAKAO);

            //then
            assertAll(
                    () -> verify(memberRepository, times(1)).save(any()),
                    () -> verify(categoryRepository, times(1)).save(any()),
                    () -> verify(memberCredentialsRepository, times(1)).save(any())
            );
        }

        @DisplayName("기존에 refreshToken이 있든 없든(회원이든 아니든), accessToken과 refreshToken은 새로 발급된다.")
        @MethodSource("provideStringsForIsBlank")
        @ParameterizedTest
        void login(Optional<RefreshToken> refreshToken) {
            //given
            final MemberInfo memberInfo = mock(MemberInfo.class);
            final String newAccessToken = "newAccessToken";
            final String newRefreshToken = "newRefreshToken";

            given(memberRepository.findBySocialIdAndSocialType(anyLong(), any(SocialType.class))).willReturn(Optional.of(memberInfo));
            given(jwtTokenProvider.createAccessToken(anyLong())).willReturn(newAccessToken);
            given(jwtTokenProvider.createRefreshToken(anyLong())).willReturn(newRefreshToken);
            given(tokenRepository.findByMemberId(any())).willReturn(refreshToken);

            //when
            final TokenResponse response = authService.login(new UserInfo(1234L, KAKAO, "nickname"), KAKAO);

            //then
            assertAll(
                    () -> assertThat(response.accessToken()).isEqualTo(newAccessToken),
                    () -> assertThat(response.refreshToken()).isEqualTo(newRefreshToken)
            );
        }

        private static Stream<Arguments> provideStringsForIsBlank() {
            return Stream.of(
                    Arguments.of(Optional.of(new RefreshToken("oldRefreshToken", null))),
                    Arguments.of(Optional.empty())
            );
        }
    }

    @Nested
    class ReissueAccessTokenAndRefreshTokenTest {
        private Member member;
        private RefreshToken refreshToken;

        @BeforeEach
        void setUp() {
            // given
            member = mock(Member.class);
            refreshToken = mock(RefreshToken.class);
            given(memberRepository.existsById(anyLong())).willReturn(true);
            given(tokenRepository.findByMemberId(anyLong())).willReturn(Optional.of(refreshToken));
        }

        @Test
        @DisplayName("요청 받은 리프레시 토큰과 db에 있는 리프레시 토큰의 값이 다른 경우")
        void invalidateRefreshToken() {
            // when
            given(jwtTokenProvider.getPayload(anyString())).willReturn(1L);
            given(refreshToken.isDifferentFrom(anyString())).willReturn(true);

            // then
            assertThatThrownBy(() -> authService.reissueAccessTokenAndRefreshToken("oldRefreshToken"))
                    .isInstanceOf(InvalidRefreshTokenException.class);
        }


        @Test
        @DisplayName("유효성 검사를 통과하면 accessToken과 refreshToken을 새로 발급한다. (db에 있는 refreshToken은 갱신한다)")
        void reissue() {
            //given
            final String newAccessToken = "newAccessToken";
            final String newRefreshToken = "newRefreshToken";
            given(refreshToken.isDifferentFrom(anyString())).willReturn(false);
            given(jwtTokenProvider.createAccessToken(anyLong())).willReturn(newAccessToken);
            given(jwtTokenProvider.createRefreshToken(anyLong())).willReturn(newRefreshToken);

            //when
            final TokenResponse response = authService.reissueAccessTokenAndRefreshToken("oldRefreshToken");

            //then
            assertAll(
                    () -> assertThat(response.accessToken()).isEqualTo(newAccessToken),
                    () -> assertThat(response.refreshToken()).isEqualTo(newRefreshToken)
            );
        }
    }

    @Test
    @DisplayName("리프레시 토큰이 이미 만료된 경우")
    void expiredRefreshToken() {
        // when
        given(jwtTokenProvider.getPayload(anyString())).willThrow(new ExpiredAccessTokenException());

        // then
        assertThatThrownBy(() -> authService.reissueAccessTokenAndRefreshToken("oldRefreshToken"))
                .isInstanceOf(ExpiredAccessTokenException.class);
    }
}
