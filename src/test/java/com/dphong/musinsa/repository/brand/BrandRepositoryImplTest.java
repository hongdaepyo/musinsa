package com.dphong.musinsa.repository.brand;

import static org.assertj.core.api.Assertions.assertThat;

import com.dphong.musinsa.RepositoryTest;
import com.dphong.musinsa.domain.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BrandRepositoryImplTest extends RepositoryTest {

    @Autowired
    private BrandJpaRepository brandJpaRepository;

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
}
