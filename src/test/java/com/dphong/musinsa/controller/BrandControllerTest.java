package com.dphong.musinsa.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dphong.musinsa.model.request.brand.BrandCreateRequest;
import com.dphong.musinsa.model.response.brand.BrandCreateResponse;
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
}
