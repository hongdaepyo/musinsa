package com.dphong.musinsa.controller;

import static com.dphong.musinsa.domain.ProductCategory.TOP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dphong.musinsa.IntegrationTest;
import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.model.dto.Money;
import com.dphong.musinsa.model.request.product.ProductCreateRequest;
import com.dphong.musinsa.model.request.product.ProductUpdateRequest;
import com.dphong.musinsa.repository.brand.BrandRepository;
import com.dphong.musinsa.repository.product.ProductJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;


class ProductControllerIntegrationTest extends IntegrationTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    private Brand brand;

    @BeforeEach
    void setUp() {
        brand = brandRepository.save(Brand.builder().name("testBrand").build());
    }

    @Test
    void 상품을_추가한다() throws Exception {
        // given
        ProductCreateRequest request = new ProductCreateRequest("product1", TOP, 1000, brand.getId());

        // when
        // then
        mockMvc.perform(
                post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.data.name").value("product1")
        );
        assertThat(productJpaRepository.findAll()).hasSize(1);
    }

    @Test
    void 상품을_업데이트한다() throws Exception {
        // given
        Product product = productJpaRepository.save(
                Product.builder().name("testProduct").category(TOP).brand(brand).price(Money.of(100)).build()
        );
        ProductUpdateRequest request = new ProductUpdateRequest("product1", TOP, 1000);

        // when
        // then
        mockMvc.perform(
                put("/v1/products/%s".formatted(product.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.data.status").value("SUCCESS")
        );
    }

    @Test
    void 상품을_삭제한다() throws Exception {
        // given
        Product product = productJpaRepository.save(
                Product.builder().name("testProduct").category(TOP).brand(brand).price(Money.of(100)).build()
        );
        // when
        // then
        mockMvc.perform(
                delete("/v1/products/%s".formatted(product.getId()))
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.data.status").value("SUCCESS")
        );
        assertThat(productJpaRepository.findAll()).isEmpty();
    }
}
