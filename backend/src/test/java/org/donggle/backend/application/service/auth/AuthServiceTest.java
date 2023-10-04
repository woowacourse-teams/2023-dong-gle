package org.donggle.backend.application.service.auth;

import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.TokenRepository;
import org.donggle.backend.domain.auth.JwtTokenProvider;
import org.donggle.backend.domain.auth.RefreshToken;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.oauth.SocialType;
import org.donggle.backend.infrastructure.oauth.kakao.dto.response.UserInfo;
import org.donggle.backend.ui.response.TokenResponse;
import org.junit.jupiter.api.DisplayName;
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
import static org.donggle.backend.domain.oauth.SocialType.KAKAO;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
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
    void login(Optional<RefreshToken> refreshToken) {
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
}
