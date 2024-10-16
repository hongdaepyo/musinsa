package com.dphong.musinsa.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.domain.ProductCategory;
import com.dphong.musinsa.mock.FakeProductRepository;
import com.dphong.musinsa.model.response.product.ProductResponse;
import com.dphong.musinsa.model.response.product.ProductsByCategoryResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductQueryServiceTest {

    private ProductQueryService service;
    private FakeProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new FakeProductRepository();
        service = new ProductQueryService(productRepository);
    }

    @Test
    void getLowestPriceProducts() {
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
}
