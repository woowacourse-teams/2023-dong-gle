package org.donggle.backend.ui;

import org.donggle.backend.application.repository.TokenRepository;
import org.donggle.backend.domain.auth.JwtTokenProvider;
import org.donggle.backend.infrastructure.client.tistory.TistoryApiClient;
import org.donggle.backend.support.JwtSupporter;
import org.donggle.backend.ui.response.TistoryCategoryListResposnse;
import org.donggle.backend.ui.response.TistoryCategoryListResposnse.TistoryCategoryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BlogController.class)
class BlogControllerTest {
    @Autowired
    public MockMvc mockMvc;
    @MockBean
    private TistoryApiClient tistoryApiService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private TokenRepository tokenRepository;

    @Test
    @DisplayName("tistory의 카테고리를 정상적으로 가져오면 200을 반환한다.")
    void tistoryCategoryList() throws Exception {
        //given
        final Long memberId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);
        final TistoryCategoryListResposnse tistoryCategoryListResposnse = new TistoryCategoryListResposnse(
                List.of(new TistoryCategoryResponse("1", "카테고리1"), new TistoryCategoryResponse("2", "카테고리2")));

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        given(tistoryApiService.findCategory(memberId)).willReturn(tistoryCategoryListResposnse);

        //when
        //then
        mockMvc.perform(
                        get("/blogs/tistory/category")
                                .header(AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories.size()").value(2))
                .andExpect(jsonPath("$.categories[0].id").value("1"))
                .andExpect(jsonPath("$.categories[0].name").value("카테고리1"))
                .andExpect(jsonPath("$.categories[1].id").value("2"))
                .andExpect(jsonPath("$.categories[1].name").value("카테고리2"));
    }
}