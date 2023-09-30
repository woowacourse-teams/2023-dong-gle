package org.donggle.backend.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.donggle.backend.application.repository.TokenRepository;
import org.donggle.backend.application.service.blog.PublishFacadeService;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.domain.auth.JwtTokenProvider;
import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.support.JwtSupporter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.donggle.backend.domain.blog.BlogType.MEDIUM;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PublishController.class)
class PublishControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private TokenRepository tokenRepository;
    @MockBean
    private PublishFacadeService blogService;

    @Test
    @DisplayName("tistory로 정상적으로 발행했을때 200을 반환한다.")
    void publishToTistory() throws Exception {
        //given
        final Long memberId = 1L;
        final long writingId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);
        final String categoryId = "1";
        final PublishRequest publishRequest = new PublishRequest(List.of("동글로 업로드한 글임"), "PUBLIC", "", categoryId, "");
        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        willDoNothing().given(blogService).publishWriting(memberId, writingId, BlogType.TISTORY, PublishRequest.tistory(publishRequest));

        //when
        //then
        mockMvc.perform(
                        post("/writings/{writingId}/publish/tistory", writingId)
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .content(new ObjectMapper().writeValueAsString(publishRequest))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("medium로 정상적으로 발행했을때 200을 반환한다.")
    void publishToMedium() throws Exception {
        //given
        final Long memberId = 1L;
        final long writingId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);
        final PublishRequest publishRequest = new PublishRequest(List.of("동글로 업로드한 글임"), "PUBLIC", null, null, null);
        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        willDoNothing().given(blogService).publishWriting(memberId, writingId, MEDIUM, PublishRequest.medium(publishRequest));

        //when
        //then
        mockMvc.perform(
                        post("/writings/{writingId}/publish/medium", writingId)
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .content(new ObjectMapper().writeValueAsString(publishRequest))
                )
                .andExpect(status().isOk());
    }
}