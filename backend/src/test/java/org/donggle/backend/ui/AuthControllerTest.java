package org.donggle.backend.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.donggle.backend.application.repository.TokenRepository;
import org.donggle.backend.application.service.auth.AuthFacadeService;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.donggle.backend.domain.auth.JwtTokenProvider;
import org.donggle.backend.domain.auth.RefreshToken;
import org.donggle.backend.support.JwtSupporter;
import org.donggle.backend.ui.response.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.donggle.backend.support.fix.MemberFixture.beaver;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AuthController.class, properties = "security.jwt.token.refresh-token-expire-length=10000")
class AuthControllerTest {

    @Autowired
    public MockMvc mockMvc;
    @MockBean
    private AuthFacadeService authFacadeService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private TokenRepository tokenRepository;

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
                                .content(new ObjectMapper().writeValueAsString(oAuthAccessTokenRequest)))
                .andExpect(status().isOk())
                .andExpect(header().exists("Set-Cookie"))
                .andExpect(jsonPath("$.accessToken").value(tokenResponse.accessToken()));

    }

    @Test
    @DisplayName("로그아웃이 정상적으로 처리되면 200 상태를 반환한다.")
    void logout() throws Exception {
        //given
        final Long memberId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);
        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        //when
        //then
        mockMvc.perform(
                        post("/auth/logout")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("액세스 토큰 재발급이 정상적으로 처리되면 200 상태와 새로운 토큰을 반환한다.")
    void reissueAccessToken() throws Exception {
        //given
        final Long memberId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);
        final String refreshToken = JwtSupporter.generateToken(memberId);
        final TokenResponse tokenResponse = new TokenResponse("access", "refresh");
        final Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(1L);
        given(tokenRepository.findByMemberId(memberId)).willReturn(Optional.of(new RefreshToken(refreshToken, beaver)));
        given(authFacadeService.reissueAccessTokenAndRefreshToken(memberId)).willReturn(tokenResponse);

        //when
        //then
        mockMvc.perform(
                        post("/auth/token/refresh")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .cookie(refreshTokenCookie)
                )
                .andExpect(status().isOk())
                .andExpect(header().exists("Set-Cookie"))
                .andExpect(jsonPath("$.accessToken").value(tokenResponse.accessToken()));
    }
}