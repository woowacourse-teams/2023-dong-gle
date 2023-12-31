package org.donggle.backend.ui;

import org.donggle.backend.support.JwtSupporter;
import org.donggle.backend.ui.response.MediumConnectionResponse;
import org.donggle.backend.ui.response.MemberPageResponse;
import org.donggle.backend.ui.response.NotionConnectionResponse;
import org.donggle.backend.ui.response.TistoryConnectionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends ControllerTest {
    @Test
    @DisplayName("사용자의 정보를 정상적으로 가져올때 200을 반환한다.")
    void memberPage() throws Exception {
        //given
        final Long memberId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);
        final String name = "동글";
        final TistoryConnectionResponse tistoryConnectionResponse = new TistoryConnectionResponse(true, "jeoninpyo726");
        final NotionConnectionResponse notionConnectionResponse = new NotionConnectionResponse(true);
        final MediumConnectionResponse mediumConnectionResponse = new MediumConnectionResponse(true);
        final MemberPageResponse memberPageResponse = new MemberPageResponse(
                memberId,
                name,
                tistoryConnectionResponse,
                notionConnectionResponse,
                mediumConnectionResponse);

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        given(memberService.findMemberPage(memberId)).willReturn(memberPageResponse);

        //when
        //then
        mockMvc.perform(
                        get("/member")
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("동글"))
                .andExpect(jsonPath("$.tistory.isConnected").value(true))
                .andExpect(jsonPath("$.tistory.blogName").value("jeoninpyo726"))
                .andExpect(jsonPath("$.notion.isConnected").value(true))
                .andExpect(jsonPath("$.medium.isConnected").value(true));
    }

    @Test
    @DisplayName("사용자를 정상적으로 탈퇴했을때 204를 반환한다.")
    void deleteMember() throws Exception {
        //given
        final Long memberId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        willDoNothing().given(memberService).deleteMember(memberId);

        //when
        //then
        mockMvc.perform(
                        delete("/member")
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                )
                .andExpect(status().isNoContent());
    }
}