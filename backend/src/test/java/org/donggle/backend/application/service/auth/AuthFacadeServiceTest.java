package org.donggle.backend.application.service.auth;

import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.donggle.backend.domain.oauth.SocialType;
import org.donggle.backend.infrastructure.oauth.kakao.dto.response.UserInfo;
import org.donggle.backend.ui.response.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.donggle.backend.domain.oauth.SocialType.KAKAO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AuthFacadeServiceTest {
    @Mock
    private LoginClients oauthClients;
    @Mock
    private AuthService authService;
    @InjectMocks
    private AuthFacadeService authFacadeService;

    @Test
    @DisplayName("redirectUri를 만드는 테스트")
    void createAuthorizeRedirectUri() {
        //given
        final String redirectUri = "redirect_uri";
        given(oauthClients.redirectUri(KAKAO, redirectUri)).willReturn("https://kauth.kakao.com/");

        //when
        final String result = authFacadeService.createAuthorizeRedirectUri("kakao", redirectUri);

        //then
        assertThat(result).isEqualTo("https://kauth.kakao.com/");
    }

    @Test
    @DisplayName("login 메서드 테스트")
    void login() {
        // given
        final String socialType = "KAKAO";
        final String redirectUri = "redirect_uri";
        final String code = "code";
        final OAuthAccessTokenRequest request = new OAuthAccessTokenRequest(redirectUri, code);
        final UserInfo userInfo = new UserInfo(1L, KAKAO, "홍길동");
        final TokenResponse expectedResponse = new TokenResponse("access", "refresh");

        given(oauthClients.findUserInfo(eq(KAKAO), anyString(), anyString())).willReturn(userInfo);
        given(authService.login(any(UserInfo.class), any(SocialType.class))).willReturn(expectedResponse);

        // when
        final TokenResponse actualResponse = authFacadeService.login(socialType, request);

        // then
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }


    @Test
    @DisplayName("logout 메서드 테스트")
    void testLogout() {
        // given
        final Long memberId = 1L;

        // when
        authFacadeService.logout(memberId);

        // then
        then(authService).should(times(1)).logout(memberId);
    }

    @Test
    @DisplayName("reissueAccessTokenAndRefreshToken 테스트")
    void reissueAccessTokenAndRefreshToken() {
        // given
        final TokenResponse expectedResponse = new TokenResponse("access", "refresh");
        given(authService.reissueAccessTokenAndRefreshToken("refresh")).willReturn(expectedResponse);

        // when
        final TokenResponse actualResponse = authFacadeService.reissueAccessTokenAndRefreshToken("refresh");

        // then
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}