package org.donggle.backend.application.service.auth;

import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.TokenRepository;
import org.donggle.backend.domain.auth.JwtTokenProvider;
import org.donggle.backend.domain.auth.RefreshToken;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.oauth.SocialType;
import org.donggle.backend.exception.authentication.ExpiredRefreshTokenException;
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
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.donggle.backend.domain.oauth.SocialType.KAKAO;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @InjectMocks
    private AuthService authService;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("기존에 refreshToken이 있든 없든, accessToken과 refreshToken은 새로 발급된다.")
    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    void login(final Optional<RefreshToken> refreshToken) {
        //given
        final Member member = mock(Member.class);
        final String newAccessToken = "newAccessToken";
        final String newRefreshToken = "newRefreshToken";

        given(member.getId()).willReturn(111L);
        given(memberRepository.findBySocialIdAndSocialType(anyLong(), any(SocialType.class))).willReturn(Optional.of(member));
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

    @Nested
    class ReissueAccessTokenAndRefreshTokenTest {
        private Member member;
        private RefreshToken refreshToken;

        @BeforeEach
        void setUp() {
            // given
            member = mock(Member.class);
            refreshToken = mock(RefreshToken.class);
            given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
            given(tokenRepository.findByMemberId(anyLong())).willReturn(Optional.of(refreshToken));
        }

        @ParameterizedTest
        @DisplayName("refreshToken에 대한 유효성을 검사한다.")
        @MethodSource("provideValidateRefreshToken")
        void validateRefreshToken(
                final boolean isDifferent,
                final boolean isInvalid,
                final Class<?> exceptionType
        ) {
            // when
            lenient().when(jwtTokenProvider.getPayload(anyString())).thenReturn(1L);
            lenient().when(refreshToken.isDifferentFrom(anyString())).thenReturn(isDifferent);
            lenient().when(jwtTokenProvider.inValidTokenUsage(anyString())).thenReturn(isInvalid);

            // then
            assertThatThrownBy(() -> authService.reissueAccessTokenAndRefreshToken("oldRefreshToken"))
                    .isInstanceOf(exceptionType);
        }

        private static Stream<Arguments> provideValidateRefreshToken() {
            return Stream.of(
                    Arguments.of(true, true, Object.class),
                    Arguments.of(true, false, InvalidRefreshTokenException.class),
                    Arguments.of(false, true, ExpiredRefreshTokenException.class)
            );
        }

        @Test
        @DisplayName("유효성 검사를 통과하면 accessToken과 refreshToken을 새로 발급한다. (db에 있는 refreshToken은 갱신한다)")
        void reissue() {
            //given
            final String newAccessToken = "newAccessToken";
            final String newRefreshToken = "newRefreshToken";
            given(refreshToken.isDifferentFrom(anyString())).willReturn(false);
            given(jwtTokenProvider.inValidTokenUsage(anyString())).willReturn(false);
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
}
