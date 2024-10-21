package com.dphong.musinsa.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dphong.musinsa.model.dto.BrandUpdateStatus;
import com.dphong.musinsa.model.dto.CommonStatus;
import com.dphong.musinsa.model.request.brand.BrandCreateRequest;
import com.dphong.musinsa.model.request.brand.BrandUpdateRequest;
import com.dphong.musinsa.model.response.brand.BrandCreateResponse;
import com.dphong.musinsa.model.response.brand.BrandDeleteResponse;
import com.dphong.musinsa.model.response.brand.BrandUpdateResponse;
import com.dphong.musinsa.service.BrandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BrandController.class)
class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandService brandService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 브랜드를_추가한다() throws Exception {
        // given
        BrandCreateRequest request = new BrandCreateRequest("테스트 브랜드");
        given(brandService.create(request)).willReturn(new BrandCreateResponse(1L, "테스트 브랜드"));

        // when
        // then
        mockMvc.perform(
                post("/v1/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.data.id").value(1),
                jsonPath("$.data.name").value("테스트 브랜드")
        );
    }

    @Test
    void 브랜드_이름이_비어있으면_브랜드를_추가할_수_없다() throws Exception {
        // given
        BrandCreateRequest request = new BrandCreateRequest("");

        // when
        // then
        mockMvc.perform(
                post("/v1/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().is4xxClientError()
        );
    }

    @Test
    void 브랜드를_업데이트한다() throws Exception {
        // given
        BrandUpdateRequest request = new BrandUpdateRequest("테스트 브랜드");
        given(brandService.update(1L, request)).willReturn(new BrandUpdateResponse(1L, BrandUpdateStatus.SUCCESS));

        // when
        // then
        mockMvc.perform(
                put("/v1/brands/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.data.status").value("SUCCESS")
        );
    }

    @Test
    void 브랜드를_삭제한다() throws Exception {
        // given
        given(brandService.delete(1L)).willReturn(new BrandDeleteResponse(1L, CommonStatus.SUCCESS));

        // when
        // then
        mockMvc.perform(
                delete("/v1/brands/1")
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.data.status").value("SUCCESS")
        );
    }
}
