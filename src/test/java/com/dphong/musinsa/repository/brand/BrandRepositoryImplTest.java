package com.dphong.musinsa.repository.brand;

import static org.assertj.core.api.Assertions.assertThat;

import com.dphong.musinsa.RepositoryTest;
import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.domain.ProductCategory;
import com.dphong.musinsa.repository.product.ProductJpaRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BrandRepositoryImplTest extends RepositoryTest {

    @Autowired
    private BrandJpaRepository brandJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    private BrandRepositoryImpl brandRepository;

    @BeforeEach
    void setUp() {
        brandRepository = new BrandRepositoryImpl(brandJpaRepository);
    }

    @Test
    void 브랜드를_저장한다() {
        Brand newBrand = Brand.builder().name("테스트 브랜드").build();
        Brand brand = brandRepository.save(newBrand);
        assertThat(brand.getName()).isEqualTo("테스트 브랜드");
    }

    @Test
    void 브랜드를_삭제한다() {
        Brand newBrand = Brand.builder().name("테스트 브랜드").build();
        Brand brand = brandRepository.save(newBrand);
        brandRepository.delete(brand);
        assertThat(brandRepository.findByIdOrNull(brand.getId())).isNull();
    }

    @Test
    void 모든_카테고리의_총합이_가장_작은_브랜드를_조회한다() {
        // given
        Brand brand1 = brandRepository.save(Brand.builder().name("브랜드1").build());
        productJpaRepository.saveAll(
                List.of(
                        Product.builder().price(3000).brand(brand1).category(ProductCategory.TOP).build(),
                        Product.builder().price(3000).brand(brand1).category(ProductCategory.OUTERWEAR).build(),
                        Product.builder().price(3000).brand(brand1).category(ProductCategory.PANTS).build()
                )
        ); // 총합이 9000

        Brand brand2 = brandRepository.save(Brand.builder().name("브랜드2").build());
        productJpaRepository.saveAll(
                List.of(
                        Product.builder().price(1000).brand(brand2).category(ProductCategory.TOP).build(),
                        Product.builder().price(1000).brand(brand2).category(ProductCategory.OUTERWEAR).build(),
                        Product.builder().price(1000).brand(brand2).category(ProductCategory.PANTS).build()
                )
        ); // 총합이 3000

        Brand brand3 = brandRepository.save(Brand.builder().name("브랜드3").build());
        productJpaRepository.saveAll(
                List.of(
                        Product.builder().price(2000).brand(brand3).category(ProductCategory.TOP).build(),
                        Product.builder().price(2000).brand(brand3).category(ProductCategory.OUTERWEAR).build(),
                        Product.builder().price(2000).brand(brand3).category(ProductCategory.PANTS).build()
                )
        ); // 총합이 6000

        // when
        Brand foundBrand = brandRepository.findBrandWithSumOfLowestPrices();

        // then
        assertThat(foundBrand.getName()).isEqualTo("브랜드2");
    }
}
