package org.donggle.backend.ui;

import org.donggle.backend.application.service.request.MarkdownUploadRequest;
import org.donggle.backend.application.service.request.NotionUploadRequest;
import org.donggle.backend.application.service.request.WritingModifyRequest;
import org.donggle.backend.support.JwtSupporter;
import org.donggle.backend.ui.response.WritingDetailResponse;
import org.donggle.backend.ui.response.WritingListWithCategoryResponse;
import org.donggle.backend.ui.response.WritingPropertiesResponse;
import org.donggle.backend.ui.response.WritingResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WritingControllerTest extends ControllerTest {
    @Test
    @DisplayName(".md 파일을 정상적으로 저장했을때 201을 반환한다.")
    void writingAdd_file() throws Exception {
        //given
        final Long memberId = 1L;
        final long categoryId = 1L;
        final long writingId = 1L;
        final String originalFilename = "test.md";
        final String content = "content";
        final MultipartFile multipartFile = new MockMultipartFile("file", originalFilename, "text/plain", content.getBytes());
        final String accessToken = JwtSupporter.generateToken(memberId);

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        given(writingFacadeService.uploadMarkDownFile(eq(memberId), any(MarkdownUploadRequest.class))).willReturn(writingId);

        //when
        //then
        mockMvc.perform(
                        multipart("/writings/file")
                                .file("file", multipartFile.getBytes())
                                .param("categoryId", String.valueOf(categoryId))
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .contentType(MULTIPART_FORM_DATA_VALUE)
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/writings/" + writingId));
    }

    @Test
    @DisplayName("notion의 글을 정상적으로 저장했을때 201을 반환한다.")
    void writingAdd_notion() throws Exception {
        //given
        final Long memberId = 1L;
        final long categoryId = 1L;
        final long writingId = 1L;
        final String blockId = "block_id";
        final NotionUploadRequest notionUploadRequest = new NotionUploadRequest(blockId, categoryId);
        final String accessToken = JwtSupporter.generateToken(memberId);

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        given(writingFacadeService.uploadNotionPage(memberId, notionUploadRequest)).willReturn(writingId);

        //when
        //then
        mockMvc.perform(
                        post("/writings/notion")
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(notionUploadRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/writings/" + writingId));
    }

    @Test
    @DisplayName("Home 페이지가 정상적으로 반환되면 200을 반환한다.")
    void showHomePage() throws Exception {
        //given
        final Long memberId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);
        final Pageable pageable = PageRequest.of(0, 12);

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        given(writingFacadeService.findAll(memberId, pageable)).willReturn(Page.empty());

        //when
        //then
        mockMvc.perform(
                        get("/writings/home")
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Writing의 Detail이 정상적으로 반환되면 200을 반환한다.")
    void writingDetails() throws Exception {
        //given
        final Long memberId = 1L;
        final long writingId = 1L;
        final long categoryId = 1L;
        final String title = "title";
        final String content = "content";
        final String accessToken = JwtSupporter.generateToken(memberId);
        final WritingResponse writingResponse = new WritingResponse(writingId, title, content, categoryId);

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        given(writingFacadeService.findWriting(memberId, writingId)).willReturn(writingResponse);

        //when
        //then
        mockMvc.perform(
                        get("/writings/" + writingId)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(writingId))
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.categoryId").value(categoryId));
    }

    @Test
    @DisplayName("Writing이 정상적으로 수정되면 204를 반환한다.")
    void writingModify() throws Exception {
        //given
        final Long memberId = 1L;
        final long writingId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);
        final WritingModifyRequest request = new WritingModifyRequest("new title", null, null);

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        willDoNothing().given(writingFacadeService).modifyWritingTitle(memberId, writingId, request);

        //when
        //then
        mockMvc.perform(
                        patch("/writings/" + writingId)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Writing의 Properties가 정상적으로 반환되면 200을 반환한다.")
    void writingPropertiesDetails() throws Exception {
        //given
        final Long memberId = 1L;
        final long writingId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);
        final WritingModifyRequest request = new WritingModifyRequest(null, 2L, 3L);
        final WritingPropertiesResponse writingPropertiesResponse = new WritingPropertiesResponse(LocalDateTime.now(), List.of());
        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        given(writingFacadeService.findWritingProperties(memberId, writingId)).willReturn(writingPropertiesResponse);

        //when
        //then
        mockMvc.perform(
                        get("/writings/" + writingId + "/properties")
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Category별 Writing List가 정상적으로 반환되면 200을 반환한다.")
    void writingListWithCategory() throws Exception {
        //given
        final Long memberId = 1L;
        final long categoryId = 1L;
        final String categoryName = "donggle";
        final String accessToken = JwtSupporter.generateToken(memberId);
        final WritingListWithCategoryResponse writingListWithCategoryResponse = new WritingListWithCategoryResponse(categoryId, categoryName,
                List.of(new WritingDetailResponse(1L, "title", LocalDateTime.now(), List.of())));
        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        given(writingFacadeService.findWritingListByCategoryId(memberId, categoryId)).willReturn(writingListWithCategoryResponse); // 예시: 빈 WritingListWithCategoryResponse 반환

        //when
        //then
        mockMvc.perform(
                        get("/writings")
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .param("categoryId", String.valueOf(categoryId))
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

}