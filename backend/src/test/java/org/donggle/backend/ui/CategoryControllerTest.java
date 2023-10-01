package org.donggle.backend.ui;

import org.donggle.backend.application.service.request.CategoryAddRequest;
import org.donggle.backend.application.service.request.CategoryModifyRequest;
import org.donggle.backend.support.JwtSupporter;
import org.donggle.backend.ui.response.CategoryListResponse;
import org.donggle.backend.ui.response.CategoryResponse;
import org.donggle.backend.ui.response.CategoryWritingsResponse;
import org.donggle.backend.ui.response.WritingSimpleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest extends ControllerTest {
    @Test
    @DisplayName("정상적으로 카테고리가 추가되면 201을 반환한다.")
    void categoryAdd() throws Exception {
        //given
        final Long memberId = 1L;
        final long categoryId = 1L;
        final String categoryName = "비빙";
        final CategoryAddRequest categoryAddRequest = new CategoryAddRequest(categoryName);
        final String accessToken = JwtSupporter.generateToken(memberId);

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        given(categoryService.addCategory(memberId, categoryAddRequest)).willReturn(categoryId);

        //when
        //then
        mockMvc.perform(
                        post("/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .content(objectMapper.writeValueAsString(categoryAddRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/categories/" + categoryId));
    }

    @Test
    @DisplayName("카테고리의 이름이 정상적으로 수정되면 203을 반환한다.")
    void categoryModifyCategoryName() throws Exception {
        //given
        final Long memberId = 1L;
        final long categoryId = 1L;
        final String categoryName = "비빙";
        final CategoryModifyRequest categoryModifyRequest = new CategoryModifyRequest(categoryName, null);
        final String accessToken = JwtSupporter.generateToken(memberId);

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        willDoNothing().given(categoryService).modifyCategoryName(memberId, categoryId, categoryModifyRequest);

        //when
        //then
        mockMvc.perform(
                        patch("/categories/" + categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .content(objectMapper.writeValueAsString(categoryModifyRequest))
                )
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("카테고리의 순서를 정상적으로 수정하면 203을 반환한다.")
    void categoryModifyCategoryOrder() throws Exception {
        //given
        final Long memberId = 1L;
        final long categoryId = 1L;
        final long nextCategoryId = 1L;
        final CategoryModifyRequest categoryModifyRequest = new CategoryModifyRequest(null, nextCategoryId);
        final String accessToken = JwtSupporter.generateToken(memberId);

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        willDoNothing().given(categoryService).modifyCategoryOrder(memberId, categoryId, categoryModifyRequest);

        //when
        //then
        mockMvc.perform(
                        patch("/categories/" + categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .content(objectMapper.writeValueAsString(categoryModifyRequest))
                )
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("카테고리 수정 시 요청 정보가 없으면 400을 반환한다.")
    void categoryModify_Exception() throws Exception {
        //given
        final Long memberId = 1L;
        final long categoryId = 1L;
        final CategoryModifyRequest categoryModifyRequest = new CategoryModifyRequest(null, null);
        final String accessToken = JwtSupporter.generateToken(memberId);

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        willDoNothing().given(categoryService).modifyCategoryOrder(memberId, categoryId, categoryModifyRequest);

        //when
        //then
        mockMvc.perform(
                        patch("/categories/" + categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                .content(objectMapper.writeValueAsString(categoryModifyRequest))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("카테고리를 정상적으로 삭제했을 때 204를 반환한다.")
    void categoryRemove() throws Exception {
        //given
        final Long memberId = 1L;
        final long categoryId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        willDoNothing().given(categoryService).removeCategory(memberId, categoryId);

        //when
        //then
        mockMvc.perform(
                        delete("/categories/" + categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("카테고리 리스트를 정상적으로 가져왔을때 200을 반환한다.")
    void categoryList() throws Exception {
        //given
        final Long memberId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);
        final CategoryListResponse categoryListResponse = new CategoryListResponse(
                List.of(new CategoryResponse(1L, "잉표"),
                        new CategoryResponse(2L, "천씨"),
                        new CategoryResponse(3L, "헙"),
                        new CategoryResponse(4L, "파덜")));

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        given(categoryService.findAll(memberId)).willReturn(categoryListResponse);

        //when
        //then
        mockMvc.perform(
                        get("/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories[0].id").value(1L))
                .andExpect(jsonPath("$.categories[0].categoryName").value("잉표"))
                .andExpect(jsonPath("$.categories[1].id").value(2L))
                .andExpect(jsonPath("$.categories[1].categoryName").value("천씨"))
                .andExpect(jsonPath("$.categories[2].id").value(3L))
                .andExpect(jsonPath("$.categories[2].categoryName").value("헙"))
                .andExpect(jsonPath("$.categories[3].id").value(4L))
                .andExpect(jsonPath("$.categories[3].categoryName").value("파덜"));
    }

    @Test
    @DisplayName("카테고리 내에 글의 정보를 정상적으로 반환했을때 200을 반환한다.")
    void categoryWritingList() throws Exception {
        //given
        final Long memberId = 1L;
        final long categoryId = 1L;
        final String accessToken = JwtSupporter.generateToken(memberId);
        final String categoryName = "동글";
        final CategoryWritingsResponse categoryWritingsResponse = new CategoryWritingsResponse(
                categoryId,
                categoryName,
                List.of(new WritingSimpleResponse(1L, "잉표"),
                        new WritingSimpleResponse(2L, "비버")));

        given(jwtTokenProvider.getPayload(accessToken)).willReturn(memberId);
        given(categoryService.findAllWritings(memberId, categoryId)).willReturn(categoryWritingsResponse);

        //when
        //then
        mockMvc.perform(
                        get("/categories/" + categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + accessToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.categoryName").value("동글"))
                .andExpect(jsonPath("$.writings[0].id").value(1L))
                .andExpect(jsonPath("$.writings[0].title").value("잉표"))
                .andExpect(jsonPath("$.writings[1].id").value(2L))
                .andExpect(jsonPath("$.writings[1].title").value("비버"));
    }
}