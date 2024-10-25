package com.dphong.musinsa.controller;

import static com.dphong.musinsa.domain.ProductCategory.TOP;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dphong.musinsa.model.dto.Money;
import com.dphong.musinsa.model.response.product.*;
import com.dphong.musinsa.service.ProductQueryService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ProductSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductQueryService productQueryService;

    @Test
    void 카테고리별_최저가_상품을_조회한다() throws Exception {
        // given
        ProductResponse response = new ProductResponse(TOP.getDescription(), "product1", "brand", Money.of(1000));
        given(productQueryService.getLowestPriceProducts()).willReturn(
                new ProductsByCategoryResponse(List.of(response), new Money(1000))
        );

        // when
        // then
        mockMvc.perform(get("/v1/products/lowest"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.data.products[0].name").value("product1"),
                        jsonPath("$.data.totalAmount").value("1,000")
                );
    }

    @Test
    void 상품_총합이_최저가인_브랜드를_조회한다() throws Exception {
        // given
        var response = new BrandProductWithLowestSumOfPricesResponse(
                new BrandLowestPriceProductResponse(
                        "brandName",
                        List.of(new CategoryProductResponse("categoryName", "productName", Money.of(1000))),
                        Money.of(1000)
                )
        );
        given(productQueryService.getBrandProductsWithLowestPrice()).willReturn(response);

        // when
        // then
        mockMvc.perform(get("/v1/products/lowest/brand"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.data.lowestPrice.categories[0].categoryName").value("categoryName"),
                        jsonPath("$.data.lowestPrice.totalAmount").value("1,000")
                );
    }

    @Test
    void 카테고리_이름으로_상품을_조회한다() throws Exception {
        // given
        given(productQueryService.getProductsByCategory(TOP)).willReturn(
                new ProductByCategoryNameResponse(
                        "상의",
                        List.of(new BrandProductResponse("brandName1", Money.of(1000))),
                        List.of(new BrandProductResponse("brandName2", Money.of(2000)))
                )
        );

        // when
        // then
        mockMvc.perform(
                        get("/v1/products/search-by-category")
                                .queryParam("name", "상의")
                )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.data.categoryName").value("상의"),
                        jsonPath("$.data.lowestPrice[0].brandName").value("brandName1"),
                        jsonPath("$.data.lowestPrice[0].price").value("1,000"),
                        jsonPath("$.data.highestPrice[0].brandName").value("brandName2"),
                        jsonPath("$.data.highestPrice[0].price").value("2,000")
                );
    }
}
