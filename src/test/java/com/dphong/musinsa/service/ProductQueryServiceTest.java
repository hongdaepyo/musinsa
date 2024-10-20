package com.dphong.musinsa.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.domain.ProductCategory;
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
                Product.builder().price(1000).name("product1").category(ProductCategory.TOP).brand(brand).build(),
                Product.builder().price(2000).name("product2").category(ProductCategory.TOP).brand(brand).build(),
                Product.builder().price(3000).name("product3").category(ProductCategory.OUTERWEAR).brand(brand).build(),
                Product.builder().price(4000).name("product4").category(ProductCategory.OUTERWEAR).brand(brand).build(),
                Product.builder().price(6000).name("product5").category(ProductCategory.BAG).brand(brand).build(),
                Product.builder().price(5000).name("product6").category(ProductCategory.BAG).brand(brand).build()
        ).forEach(productRepository::save);

        // when
        ProductsByCategoryResponse response = service.getLowestPriceProducts();

        // then
        assertThat(response.products())
                .hasSize(3)
                .containsExactly(
                        new ProductResponse(ProductCategory.TOP, "product1", "brand", 1000),
                        new ProductResponse(ProductCategory.OUTERWEAR, "product3", "brand", 3000),
                        new ProductResponse(ProductCategory.BAG, "product6", "brand", 5000)
                );
        assertThat(response.totalAmount()).isEqualTo(9000);
    }

    @Test
    void 브랜드의_카테고리별_최저가_상품을_조회한다() {
        // given
        Brand brand = brandRepository.save(Brand.builder().id(1L).name("brand").build());
        Brand brand2 = brandRepository.save(Brand.builder().id(2L).name("brand2").build());
        List.of(
                Product.builder().price(1000).name("product1").category(ProductCategory.TOP).brand(brand).build(),
                Product.builder().price(4000).name("product4").category(ProductCategory.OUTERWEAR).brand(brand).build(),
                Product.builder().price(5000).name("product6").category(ProductCategory.BAG).brand(brand).build(),
                Product.builder().price(1).name("product7").category(ProductCategory.TOP).brand(brand2).build(),
                Product.builder().price(3).name("product7").category(ProductCategory.OUTERWEAR).brand(brand2).build(),
                Product.builder().price(6).name("product7").category(ProductCategory.BAG).brand(brand2).build()
        ).forEach(productRepository::save);

        // when
        BrandLowestPriceProductResponse response = service.getLowestPriceProductsByBrand(brand.getId());

        // then
        assertThat(response.products())
                .hasSize(3)
                .containsExactly(
                        new CategoryProductResponse(ProductCategory.TOP.getDescription(), "product1", 1000),
                        new CategoryProductResponse(ProductCategory.OUTERWEAR.getDescription(), "product4", 4000),
                        new CategoryProductResponse(ProductCategory.BAG.getDescription(), "product6", 5000)
                );
        assertThat(response.totalAmount()).isEqualTo(10000);
    }

    @Test
    void 카테고리의_최저가_최고가_상품을_조회한다() {
        // given
        Brand brand = brandRepository.save(Brand.builder().id(1L).name("brand").build());
        List.of(
                Product.builder().price(1000).name("product1").category(ProductCategory.TOP).brand(brand).build(),
                Product.builder().price(2000).name("product2").category(ProductCategory.TOP).brand(brand).build(),
                Product.builder().price(3000).name("product3").category(ProductCategory.TOP).brand(brand).build(),
                Product.builder().price(3000).name("product4").category(ProductCategory.HAT).brand(brand).build(),
                Product.builder().price(4000).name("product5").category(ProductCategory.HAT).brand(brand).build()
        ).forEach(productRepository::save);

        // when
        ProductByCategoryNameResponse response = service.getProductsByCategory(ProductCategory.TOP);

        // then
        assertThat(response.categoryName()).isEqualTo("상의");
        assertThat(response.lowestPrice().getFirst()).isEqualTo(new BrandProductResponse(brand.getName(), 1000));
        assertThat(response.highestPrice().getFirst()).isEqualTo(new BrandProductResponse(brand.getName(), 3000));
    }
}
