package com.dphong.musinsa.repository.product;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.dphong.musinsa.RepositoryTest;
import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.domain.ProductCategory;
import com.dphong.musinsa.repository.brand.BrandJpaRepository;
import com.dphong.musinsa.repository.brand.BrandRepository;
import com.dphong.musinsa.repository.brand.BrandRepositoryImpl;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProductRepositoryImplTest extends RepositoryTest {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private BrandJpaRepository brandJpaRepository;

    private ProductRepository productRepository;

    private BrandRepository brandRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepositoryImpl(productJpaRepository);
        brandRepository = new BrandRepositoryImpl(brandJpaRepository);
    }

    @Test
    void 상품_아이디로_상품을_조회한다() {
        // given
        Product product = productRepository.save(
                Product.builder().price(1000).name("product1").category(ProductCategory.TOP).build()
        );
        // when

        Product foundProduct = productRepository.findByIdOrNull(product.getId());

        // then
        assertThat(foundProduct).isEqualTo(product);
    }

    @Test
    void 카테고리별_최저가_상품을_조회한다() {
        // given
        Brand brand = brandRepository.save(Brand.builder().name("brand").build());
        List.of(
                Product.builder().price(1000).name("product1").category(ProductCategory.TOP).brand(brand).build(),
                Product.builder().price(2000).name("product2").category(ProductCategory.TOP).brand(brand).build(),
                Product.builder().price(3000).name("product3").category(ProductCategory.OUTERWEAR).brand(brand).build(),
                Product.builder().price(4000).name("product4").category(ProductCategory.OUTERWEAR).brand(brand).build(),
                Product.builder().price(6000).name("product5").category(ProductCategory.BAG).brand(brand).build(),
                Product.builder().price(5000).name("product6").category(ProductCategory.BAG).brand(brand).build()
        ).forEach(productRepository::save);

        // when
        List<Product> products = productRepository.findAllLowestPriceProductsByCategory();

        // then
        assertThat(products).hasSize(3);
        assertThat(products.getFirst().getName()).isEqualTo("product1");
        assertThat(products.get(1).getName()).isEqualTo("product3");
        assertThat(products.getLast().getName()).isEqualTo("product6");
    }
    
    @Test
    void 카테고리의_최저가_상품을_조회한다() {
        // given
        List.of(
                Product.builder().price(1000).name("product1").category(ProductCategory.TOP).build(),
                Product.builder().price(2000).name("product2").category(ProductCategory.TOP).build(),
                Product.builder().price(3000).name("product3").category(ProductCategory.HAT).build(),
                Product.builder().price(4000).name("product4").category(ProductCategory.HAT).build()
        ).forEach(productRepository::save);

        // when
        Product product = productRepository.findLowestPriceProductByCategory(ProductCategory.TOP);

        // then
        assertThat(product.getName()).isEqualTo("product1");
        assertThat(product.getCategory()).isEqualTo(ProductCategory.TOP);
        assertThat(product.getPrice()).isEqualTo(1000);
    }

    @Test
    void 카테고리의_최고가_상품을_조회한다() {
        // given
        List.of(
                Product.builder().price(1000).name("product1").category(ProductCategory.TOP).build(),
                Product.builder().price(2000).name("product2").category(ProductCategory.TOP).build(),
                Product.builder().price(3000).name("product3").category(ProductCategory.HAT).build(),
                Product.builder().price(4000).name("product4").category(ProductCategory.HAT).build()
        ).forEach(productRepository::save);

        // when
        Product product = productRepository.findHighestPriceProductByCategory(ProductCategory.HAT);

        // then
        assertThat(product.getName()).isEqualTo("product4");
        assertThat(product.getCategory()).isEqualTo(ProductCategory.HAT);
        assertThat(product.getPrice()).isEqualTo(4000);
    }

    @Test
    void 상품을_삭제한다() {
        // given
        Product product = productRepository.save(
                Product.builder().price(1000).name("product1").category(ProductCategory.TOP).build()
        );

        // when
        productRepository.delete(product);

        // then
        Assertions.assertThat(productRepository.findByIdOrNull(product.getId())).isNull();
    }
}
