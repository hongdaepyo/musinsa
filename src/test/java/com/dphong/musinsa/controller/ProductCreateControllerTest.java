package com.dphong.musinsa.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dphong.musinsa.domain.ProductCategory;
import com.dphong.musinsa.model.request.product.ProductCreateRequest;
import com.dphong.musinsa.model.response.product.ProductCreateResponse;
import com.dphong.musinsa.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductCreateController.class)
class ProductCreateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 상품을_추가한다() throws Exception {
        // given
        ProductCreateRequest request = new ProductCreateRequest("product1", ProductCategory.TOP, 1000, 1L);
        given(productService.create(request)).willReturn(new ProductCreateResponse(1L, "product1"));

        // when
        // then
        mockMvc.perform(
                post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.data.id").value("1"),
                jsonPath("$.data.name").value("product1")
        );
    }

    @Test
    void 상품을_추가할_때_상품명이_비어있으면_상품을_추가할_수_없다() throws Exception {
        // given
        ProductCreateRequest request = new ProductCreateRequest("", ProductCategory.TOP, 1000, 1L);

        // when
        // then
        mockMvc.perform(
                post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().is4xxClientError()
        );
    }

    @Test
    void 상품을_추가할_때_금액이_0보다_작으면_상품을_추가할_수_없다() throws Exception {
        // given
        ProductCreateRequest request = new ProductCreateRequest("product1", ProductCategory.TOP, -1000, 1L);

        // when
        // then
        mockMvc.perform(
                post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().is4xxClientError()
        );
    }
}
