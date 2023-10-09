package org.donggle.backend.ui;

import jakarta.servlet.http.Cookie;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.donggle.backend.domain.auth.RefreshToken;
import org.donggle.backend.support.JwtSupporter;
import org.donggle.backend.ui.response.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.donggle.backend.support.fix.MemberFixture.beaver_have_id;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends ControllerTest {
    @Test
    @DisplayName("OAuth로그인 시 Redirect uri를 정상적으로 만들면 302 반환")
    void createRedirect() throws Exception {
        //given
        final String redirectUri = "https://kauth.kakao.com/";

        given(authFacadeService.createAuthorizeRedirectUri(anyString(), anyString())).willReturn(redirectUri);

        //when
        //then
        mockMvc.perform(
                        get("/auth/login/{socialType}/redirect", "kakao")
                                .param("redirect_uri", "someUri")
                )
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, redirectUri));
    }

    @Test
    @DisplayName("OAuth 로그인이 정상적으로 처리되면 200 상태와 토큰을 반환한다.")
    void login() throws Exception {
        //given
        final String socialType = "kakao";
        final OAuthAccessTokenRequest oAuthAccessTokenRequest = new OAuthAccessTokenRequest("redirect_uri", "code");
        final TokenResponse tokenResponse = new TokenResponse("access", "refresh");

        given(authFacadeService.login(socialType, oAuthAccessTokenRequest)).willReturn(tokenResponse);
        //when
        //then
        mockMvc.perform(
                        post("/auth/login/{socialType}", socialType)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(oAuthAccessTokenRequest)))
                .andExpect(status().isOk())
                .andExpect(header().exists("Set-Cookie"))
                .andExpect(jsonPath("$.accessToken").value(tokenResponse.accessToken()));

    }

    @Test
    @DisplayName("로그아웃이 정상적으로 처리되면 200 상태를 반환한다.")
    void logout_with_refreshToken() throws Exception {
        //given
        final Long memberId = 1L;
        final String refreshToken = JwtSupporter.generateToken(memberId);

        //when, then
        mockMvc.perform(
                        post("/auth/logout")
                                .contentType(MediaType.APPLICATION_JSON)
                                .cookie(new Cookie("refreshToken", refreshToken))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("쿠키에 리프레시 토큰이 없더라도, 로그아웃시 정상적으로 200 상태를 반환한다.")
    void logout_without_refreshToken() throws Exception {
        mockMvc.perform(
                        post("/auth/logout").contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("액세스 토큰 재발급이 정상적으로 처리되면 200 상태와 새로운 토큰을 반환한다.")
    void reissueAccessToken_with_refreshToken() throws Exception {
        //given
        final Long memberId = 1L;
        final String refreshToken = JwtSupporter.generateToken(memberId);
        final TokenResponse expectedResponse = new TokenResponse("access", "refresh");
        final Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);

        given(tokenRepository.findByMemberId(memberId)).willReturn(Optional.of(new RefreshToken(refreshToken, beaver_have_id)));
        given(authFacadeService.reissueAccessTokenAndRefreshToken(refreshToken)).willReturn(expectedResponse);

        //when, then
        mockMvc.perform(
                        post("/auth/token/refresh")
                                .contentType(MediaType.APPLICATION_JSON)
                                .cookie(refreshTokenCookie)
                )
                .andExpect(status().isOk())
                .andExpect(header().string("Set-Cookie", containsString("refreshToken=" + expectedResponse.refreshToken())))
                .andExpect(jsonPath("$.accessToken").value(expectedResponse.accessToken()));
    }

    @Test
    @DisplayName("리프래시 토큰이 만료되었을 때에는 예외가 발생한다.")
    void reissueAccessToken_without_refreshToken() throws Exception {
        mockMvc.perform(
                        post("/auth/token/refresh").contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(401))
                .andExpect(jsonPath("$.error.message").value("토큰이 만료되었습니다."))
                .andExpect(jsonPath("$.error.hint").value("RefreshToken이 만료되었습니다. 다시 로그인을 진행하세요."))
                .andExpect(jsonPath("$.error.code").value(4012));
    }
}
