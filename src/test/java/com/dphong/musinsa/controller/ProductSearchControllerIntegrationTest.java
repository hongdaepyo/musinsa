package com.dphong.musinsa.controller;

import static com.dphong.musinsa.domain.ProductCategory.OUTERWEAR;
import static com.dphong.musinsa.domain.ProductCategory.PANTS;
import static com.dphong.musinsa.domain.ProductCategory.TOP;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dphong.musinsa.IntegrationTest;
import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.fixtures.BrandFixture;
import com.dphong.musinsa.repository.brand.BrandJpaRepository;
import com.dphong.musinsa.repository.product.ProductJpaRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProductSearchControllerIntegrationTest extends IntegrationTest {

    @Autowired
    private BrandJpaRepository brandJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Test
    void 카테고리별_최저가_상품을_조회한다() throws Exception {
        // given
        Brand brand = brandJpaRepository.save(
                BrandFixture.create().build()
        );
        Brand brand2 = brandJpaRepository.save(BrandFixture.create().name("testBrand2").build());

        productJpaRepository.saveAll(
                List.of(
                        Product.builder().brand(brand).category(TOP).price(1000).name("testTop1").build(),
                        Product.builder().brand(brand).category(OUTERWEAR).price(1500).name("testOuter1").build(),
                        Product.builder().brand(brand).category(PANTS).price(2000).name("testPants1").build(),
                        Product.builder().brand(brand2).category(TOP).price(5000).name("testTop2").build(),
                        Product.builder().brand(brand2).category(OUTERWEAR).price(1000).name("testOuter2").build(),
                        Product.builder().brand(brand2).category(PANTS).price(3000).name("testPants2").build()
                )
        );

        // when
        // then
        mockMvc.perform(get("/v1/products/lowest"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.data.products[0].name").value("testTop1"),
                        jsonPath("$.data.products[1].name").value("testOuter2"),
                        jsonPath("$.data.products[2].name").value("testPants1"),
                        jsonPath("$.data.totalAmount").value(4000)
                );
    }

    @Test
    void 상품_총합이_최저가인_브랜드를_조회한다() throws Exception {
        // given
        Brand brand1 = BrandFixture.create().build();
        brandJpaRepository.save(
                brand1.addProducts(
                        List.of(
                                Product.builder().brand(brand1).category(TOP).price(1000).name("testTop1").build(),
                                Product.builder().brand(brand1).category(OUTERWEAR).price(5000).name("testOuter1")
                                        .build(),
                                Product.builder().brand(brand1).category(PANTS).price(1000).name("testPants1").build()
                        )
                )
        );

        Brand brand2 = BrandFixture.create().build();
        brandJpaRepository.save(
                brand2.addProducts(
                        List.of(
                                Product.builder().brand(brand2).category(TOP).price(2000).name("testTop2").build(),
                                Product.builder().brand(brand2).category(OUTERWEAR).price(3500).name("testOuter2").build(),
                                Product.builder().brand(brand2).category(PANTS).price(2000).name("testPants2").build()
                        )
                )
        );

        Brand brand3 = brandJpaRepository.save(BrandFixture.create().name("testBrand3").build());
        brandJpaRepository.save(
                brand3.addProducts(
                        List.of(
                                Product.builder().brand(brand3).category(TOP).price(3000).name("testTop3").build(),
                                Product.builder().brand(brand3).category(OUTERWEAR).price(2000).name("testOuter3")
                                        .build(),
                                Product.builder().brand(brand3).category(PANTS).price(3000).name("testPants3").build()
                        )
                )
        );

        // when
        // then
        mockMvc.perform(get("/v1/products/lowest/brand"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.data.lowestPrice.categories[0].categoryName").value("상의"),
                        jsonPath("$.data.lowestPrice.totalAmount").value(7000)
                );
    }

    @Test
    void 카테고리_이름으로_상품을_조회한다() throws Exception {
        // given
        Brand brand = brandJpaRepository.save(BrandFixture.create().build());
        Brand brand2 = brandJpaRepository.save(BrandFixture.create().name("testBrand2").build());

        productJpaRepository.saveAll(
                List.of(
                        Product.builder().brand(brand).category(TOP).price(1000).name("testTop1").build(),
                        Product.builder().brand(brand2).category(TOP).price(2000).name("testTop2").build(),
                        Product.builder().brand(brand).category(OUTERWEAR).price(5000).name("testOuter1").build(),
                        Product.builder().brand(brand2).category(OUTERWEAR).price(3500).name("testOuter2").build(),
                        Product.builder().brand(brand).category(PANTS).price(1000).name("testPants1").build(),
                        Product.builder().brand(brand2).category(PANTS).price(2000).name("testPants2").build()
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
                        jsonPath("$.data.lowestPrice[0].brandName").value("testBrand"),
                        jsonPath("$.data.lowestPrice[0].price").value(1000),
                        jsonPath("$.data.highestPrice[0].brandName").value("testBrand2"),
                        jsonPath("$.data.highestPrice[0].price").value(2000)
                );
    }
}
