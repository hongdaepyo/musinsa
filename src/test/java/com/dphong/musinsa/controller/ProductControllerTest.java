package com.dphong.musinsa.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dphong.musinsa.domain.ProductCategory;
import com.dphong.musinsa.model.response.product.BrandLowestPriceProductResponse;
import com.dphong.musinsa.model.response.product.CategoryProductResponse;
import com.dphong.musinsa.model.response.product.ProductResponse;
import com.dphong.musinsa.model.response.product.ProductsByCategoryResponse;
import com.dphong.musinsa.service.ProductQueryService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductQueryService productQueryService;

    @Test
    void products() throws Exception {
        // given

        // when
        // then
        mockMvc.perform(get("/v1/products"))
                .andExpect(
                        status().isOk()
                );
    }

    @Test
    void 카테고리별_최저가_상품을_조회한다() throws Exception {
        // given
        given(productQueryService.getLowestPriceProducts()).willReturn(
                new ProductsByCategoryResponse(
                        List.of(new ProductResponse(ProductCategory.TOP, "product1", "brand", 1000)),
                        1000
                )
        );

        // when
        // then
        mockMvc.perform(get("/v1/products/lowest"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.data.products[0].name").value("product1"),
                        jsonPath("$.data.totalAmount").value(1000)
                );
    }

    @Test
    void 브랜드의_카테고리별_최저가_상품을_조회한다() throws Exception {
        // given
        given(productQueryService.getLowestPriceProductsByBrand(1L)).willReturn(
                new BrandLowestPriceProductResponse("brandName", List.of(
                        new CategoryProductResponse("categoryName", "productName", 1000)
                ), 1000)
        );

        // when
        // then
        mockMvc.perform(get("/v1/products/lowest/brand/1"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.data.products[0].categoryName").value("categoryName"),
                        jsonPath("$.data.totalAmount").value(1000)
                );
    }
}
