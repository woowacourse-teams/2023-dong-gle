package org.donggle.backend.ui;

import org.donggle.backend.application.service.request.WritingsDeleteRequest;
import org.donggle.backend.application.service.request.WritingsRestoreRequest;
import org.donggle.backend.support.JwtSupporter;
import org.donggle.backend.ui.response.TrashResponse;
import org.donggle.backend.ui.response.TrashedWritingResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TrashControllerTest extends ControllerTest {
    @Test
    @DisplayName("휴지통에 글목록을 정상적으로 불러왔을때 200을 반환한다.")
    void trashGetWritings() throws Exception {
        //given
        final Long memberId = 1L;
        final long writingId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);
        final TrashResponse trashResponse = new TrashResponse(List.of(
                new TrashedWritingResponse(1L, "비버 일기", 1L),
                new TrashedWritingResponse(2L, "곰이란?", 2L)));

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        given(trashService.findTrashedWritingList(memberId)).willReturn(trashResponse);

        //when
        //then
        mockMvc.perform(
                        get("/trash")
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.writings[0].id").value(1L))
                .andExpect(jsonPath("$.writings[0].title").value("비버 일기"))
                .andExpect(jsonPath("$.writings[0].categoryId").value(1L))
                .andExpect(jsonPath("$.writings[1].id").value(2L))
                .andExpect(jsonPath("$.writings[1].title").value("곰이란?"))
                .andExpect(jsonPath("$.writings[1].categoryId").value(2L));
    }

    @Test
    @DisplayName("글을 바로 정상적으로 제거했을때 200을 반환한다.")
    void DeleteWritings() throws Exception {
        //given
        final Long memberId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);
        final WritingsDeleteRequest writingsDeleteRequest = new WritingsDeleteRequest(List.of(1L, 2L), true);
        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        willDoNothing().given(trashService).deleteWritings(memberId, writingsDeleteRequest.writingIds());

        //when
        //then
        mockMvc.perform(
                        post("/trash")
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .content(objectMapper.writeValueAsString(writingsDeleteRequest))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("휴지통에서 글을 정상적으로 제거했을때 200을 반환한다.")
    void trashDeleteWritings() throws Exception {
        //given
        final Long memberId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);
        final WritingsDeleteRequest writingsDeleteRequest = new WritingsDeleteRequest(List.of(1L, 2L), false);
        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        willDoNothing().given(trashService).trashWritings(memberId, writingsDeleteRequest.writingIds());

        //when
        //then
        mockMvc.perform(
                        post("/trash")
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .content(objectMapper.writeValueAsString(writingsDeleteRequest))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("휴지통의 글을 정상적을 복구했을때 200을 반환한다.")
    void trashRestoreWritings() throws Exception {
        //given
        final Long memberId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);
        final WritingsRestoreRequest writingsRestoreRequest = new WritingsRestoreRequest(List.of(1L, 2L));
        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        willDoNothing().given(trashService).restoreWritings(memberId, writingsRestoreRequest.writingIds());

        //when
        //then
        mockMvc.perform(
                        post("/trash/restore")
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .content(objectMapper.writeValueAsString(writingsRestoreRequest))
                )
                .andExpect(status().isOk());
    }
}