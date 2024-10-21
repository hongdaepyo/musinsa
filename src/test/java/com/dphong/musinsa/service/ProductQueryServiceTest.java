package com.dphong.musinsa.service;

import static com.dphong.musinsa.domain.ProductCategory.BAG;
import static com.dphong.musinsa.domain.ProductCategory.HAT;
import static com.dphong.musinsa.domain.ProductCategory.OUTERWEAR;
import static com.dphong.musinsa.domain.ProductCategory.TOP;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.mock.FakeBrandRepository;
import com.dphong.musinsa.mock.FakeProductRepository;
import com.dphong.musinsa.model.response.product.*;
import com.dphong.musinsa.repository.brand.BrandRepository;
import com.dphong.musinsa.repository.product.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductQueryServiceTest {

    private ProductQueryService service;
    private ProductRepository productRepository;
    private BrandRepository brandRepository;

    @BeforeEach
    void setUp() {
        productRepository = new FakeProductRepository();
        brandRepository = new FakeBrandRepository();

        service = new ProductQueryService(productRepository, brandRepository);
    }

    @Test
    void 카테고리별_최저가_상품을_조회한다() {
        // given
        Brand brand = Brand.builder().name("brand").build();
        List.of(
                Product.builder().price(1000).name("product1").category(TOP).brand(brand).build(),
                Product.builder().price(2000).name("product2").category(TOP).brand(brand).build(),
                Product.builder().price(3000).name("product3").category(OUTERWEAR).brand(brand).build(),
                Product.builder().price(4000).name("product4").category(OUTERWEAR).brand(brand).build(),
                Product.builder().price(6000).name("product5").category(BAG).brand(brand).build(),
                Product.builder().price(5000).name("product6").category(BAG).brand(brand).build()
        ).forEach(productRepository::save);

        // when
        ProductsByCategoryResponse response = service.getLowestPriceProducts();

        // then
        assertThat(response.products())
                .hasSize(3)
                .containsExactly(
                        new ProductResponse(TOP.getDescription(), "product1", "brand", 1000),
                        new ProductResponse(OUTERWEAR.getDescription(), "product3", "brand", 3000),
                        new ProductResponse(BAG.getDescription(), "product6", "brand", 5000)
                );
        assertThat(response.totalAmount()).isEqualTo(9000);
    }

    @Test
    void 상품_총합이_최저가인_브랜드의_상품을_조회한다() {
        // given
        Brand brand = brandRepository.save(Brand.builder().id(1L).name("brand").build());
        Brand brand2 = brandRepository.save(Brand.builder().id(2L).name("brand2").build());
        brand.addProducts(
                List.of(
                        Product.builder().price(1000).name("product1").category(TOP).brand(brand).build(),
                        Product.builder().price(4000).name("product2").category(OUTERWEAR).brand(brand).build(),
                        Product.builder().price(5000).name("product3").category(BAG).brand(brand).build()
                )
        );
        brand2.addProducts(
                List.of(
                        Product.builder().price(1).name("product4").category(TOP).brand(brand2).build(),
                        Product.builder().price(3).name("product5").category(OUTERWEAR).brand(brand2).build(),
                        Product.builder().price(6).name("product6").category(BAG).brand(brand2).build()
                )
        );

        // when
        BrandProductWithLowestSumOfPricesResponse response = service.getBrandProductsWithLowestPrice();

        // then
        assertThat(response.lowestPrice().categories())
                .hasSize(3)
                .containsExactly(
                        new CategoryProductResponse(TOP.getDescription(), "product4", 1),
                        new CategoryProductResponse(OUTERWEAR.getDescription(), "product5", 3),
                        new CategoryProductResponse(BAG.getDescription(), "product6", 6)
                );
        assertThat(response.lowestPrice().totalAmount()).isEqualTo(10);
    }

    @Test
    void 카테고리의_최저가_최고가_상품을_조회한다() {
        // given
        Brand brand = brandRepository.save(Brand.builder().id(1L).name("brand").build());
        List.of(
                Product.builder().price(1000).name("product1").category(TOP).brand(brand).build(),
                Product.builder().price(2000).name("product2").category(TOP).brand(brand).build(),
                Product.builder().price(3000).name("product3").category(TOP).brand(brand).build(),
                Product.builder().price(3000).name("product4").category(HAT).brand(brand).build(),
                Product.builder().price(4000).name("product5").category(HAT).brand(brand).build()
        ).forEach(productRepository::save);

        // when
        ProductByCategoryNameResponse response = service.getProductsByCategory(TOP);

        // then
        assertThat(response.categoryName()).isEqualTo("상의");
        assertThat(response.lowestPrice().getFirst()).isEqualTo(new BrandProductResponse(brand.getName(), 1000));
        assertThat(response.highestPrice().getFirst()).isEqualTo(new BrandProductResponse(brand.getName(), 3000));
    }
}
