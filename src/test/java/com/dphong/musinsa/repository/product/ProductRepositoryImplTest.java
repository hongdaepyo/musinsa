package com.dphong.musinsa.repository.product;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.dphong.musinsa.common.config.JpaConfig;
import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.domain.ProductCategory;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@Import(JpaConfig.class)
@DataJpaTest
@Transactional
class ProductRepositoryImplTest {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private TestEntityManager entityManager;

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepositoryImpl(productJpaRepository);
    }

    @Test
    void getLowestPriceProducts() {
        // given
        Brand brand = entityManager.persist(Brand.builder().name("brand").build());
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
}
