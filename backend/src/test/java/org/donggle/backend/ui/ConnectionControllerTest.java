package org.donggle.backend.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.donggle.backend.application.repository.TokenRepository;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.donggle.backend.application.service.request.TokenAddRequest;
import org.donggle.backend.domain.auth.JwtTokenProvider;
import org.donggle.backend.infrastructure.client.medium.MediumConnectionClient;
import org.donggle.backend.infrastructure.client.notion.NotionConnectionClient;
import org.donggle.backend.infrastructure.client.tistory.TistoryConnectionClient;
import org.donggle.backend.support.JwtSupporter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConnectionController.class)
class ConnectionControllerTest {
    @Autowired
    public MockMvc mockMvc;
    @MockBean
    private TistoryConnectionClient tistoryConnectService;
    @MockBean
    private NotionConnectionClient notionConnectionService;
    @MockBean
    private MediumConnectionClient mediumConnectionClient;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private TokenRepository tokenRepository;

    @Test
    @DisplayName("tistory의 RedirectUri를 정상적으로 반한했을때 302를 반환한다.")
    void connectionsRedirectTistory() throws Exception {
        //given
        final String redirectUri = "redirect_uri";
        final String serviceRedirectUri = "https://donggle.blog";

        given(tistoryConnectService.createAuthorizeRedirectUri(redirectUri)).willReturn(serviceRedirectUri);

        //when
        //then
        mockMvc.perform(
                        get("/connections/tistory/redirect")
                                .param("redirect_uri", redirectUri)
                )
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, serviceRedirectUri));
    }

    @Test
    @DisplayName("tistory의 token을 정상적으로 받았을 때 200을 반환한다.")
    void connectionsAddTistory() throws Exception {
        //given
        final Long memberId = 1L;
        final String redirectUri = "redirect_uri";
        final String code = "code";
        final String accessToken = JwtSupporter.generateToken(memberId);
        final OAuthAccessTokenRequest oAuthAccessTokenRequest = new OAuthAccessTokenRequest(redirectUri, code);

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        willDoNothing().given(tistoryConnectService).saveAccessToken(memberId, oAuthAccessTokenRequest);

        //when
        //then
        mockMvc.perform(
                        post("/connections/tistory")
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .content(new ObjectMapper().writeValueAsString(oAuthAccessTokenRequest))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("tistory의 token을 정상적으로 삭제했을 때 200을 반환한다.")
    void connectionsDisconnectTistory() throws Exception {
        //given
        final Long memberId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        willDoNothing().given(tistoryConnectService).deleteAccessToken(memberId);

        //when
        //then
        mockMvc.perform(
                        post("/connections/tistory/disconnect")
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("notion의 RedirectUri를 정상적으로 반한했을때 302를 반환한다.")
    void connectionsRedirectNotion() throws Exception {
        //given
        final String redirectUri = "redirect_uri";
        final String serviceRedirectUri = "https://donggle.blog";

        given(notionConnectionService.createRedirectUri(redirectUri)).willReturn(serviceRedirectUri);

        //when
        //then
        mockMvc.perform(
                        get("/connections/notion/redirect")
                                .param("redirect_uri", redirectUri)
                )
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, serviceRedirectUri));
    }

    @Test
    @DisplayName("notion의 token을 정상적으로 받았을 때 200을 반환한다.")
    void connectionsAddNotion() throws Exception {
        //given
        final Long memberId = 1L;
        final String redirectUri = "redirect_uri";
        final String code = "code";
        final String accessToken = JwtSupporter.generateToken(memberId);
        final OAuthAccessTokenRequest oAuthAccessTokenRequest = new OAuthAccessTokenRequest(redirectUri, code);

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        willDoNothing().given(notionConnectionService).saveAccessToken(memberId, oAuthAccessTokenRequest);

        //when
        //then
        mockMvc.perform(
                        post("/connections/notion")
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .content(new ObjectMapper().writeValueAsString(oAuthAccessTokenRequest))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("notion의 token을 정상적으로 삭제했을 때 200을 반환한다.")
    void connectionsDisconnectNotion() throws Exception {
        //given
        final Long memberId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        willDoNothing().given(notionConnectionService).deleteAccessToken(memberId);

        //when
        //then
        mockMvc.perform(
                        post("/connections/notion/disconnect")
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("medium에 token을 정상적으로 저장했을때 200을 반환한다.")
    void connectionAddMedium() throws Exception {
        //given
        final Long memberId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);
        final TokenAddRequest tokenAddRequest = new TokenAddRequest("token");

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        willDoNothing().given(mediumConnectionClient).saveAccessToken(memberId, tokenAddRequest);

        //when
        //then
        mockMvc.perform(
                        post("/connections/medium")
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .content(new ObjectMapper().writeValueAsString(tokenAddRequest))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("medium의 token을 정상적으로 삭제했을 때 200을 반환한다.")
    void connectionsDisconnectMedium() throws Exception {
        //given
        final Long memberId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        willDoNothing().given(mediumConnectionClient).deleteAccessToken(memberId);

        //when
        //then
        mockMvc.perform(
                        post("/connections/medium/disconnect")
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                )
                .andExpect(status().isOk());
    }
}