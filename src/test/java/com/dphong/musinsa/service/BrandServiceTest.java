package com.dphong.musinsa.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.mock.FakeBrandRepository;
import com.dphong.musinsa.model.dto.BrandUpdateStatus;
import com.dphong.musinsa.model.dto.CommonStatus;
import com.dphong.musinsa.model.request.brand.BrandCreateRequest;
import com.dphong.musinsa.model.request.brand.BrandUpdateRequest;
import com.dphong.musinsa.model.response.brand.BrandCreateResponse;
import com.dphong.musinsa.model.response.brand.BrandDeleteResponse;
import com.dphong.musinsa.model.response.brand.BrandUpdateResponse;
import com.dphong.musinsa.repository.brand.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BrandServiceTest {

    private BrandService brandService;
    private BrandRepository brandRepository;

    @BeforeEach
    void setUp() {
        brandRepository = new FakeBrandRepository();
        brandService = new BrandService(brandRepository);
    }

    @Test
    void 브랜드를_추가한다() {
        // given
        BrandCreateRequest request = new BrandCreateRequest("테스트 브랜드");

        // when
        BrandCreateResponse response = brandService.create(request);

        // then
        assertThat(response.name()).isEqualTo("테스트 브랜드");
    }

    @Test
    void 브랜드를_업데이트한다() {
        // given
        Brand brand = brandRepository.save(Brand.builder().name("testBrand").build());
        BrandUpdateRequest request = new BrandUpdateRequest("testBrand2");
        // when
        BrandUpdateResponse response = brandService.update(brand.getId(), request);
        // then
        assertThat(response.status()).isEqualTo(BrandUpdateStatus.SUCCESS);

        Brand foundBrand = brandRepository.findByIdOrNull(brand.getId());
        assertThat(foundBrand.getName()).isEqualTo("testBrand2");
    }

    @Test
    void 브랜드를_삭제한다() {
        // given
        Brand brand = brandRepository.save(Brand.builder().name("testBrand").build());
        // when
        BrandDeleteResponse response = brandService.delete(brand.getId());
        // then
        assertThat(response.status()).isEqualTo(CommonStatus.SUCCESS);

        assertThat(brandRepository.findByIdOrNull(brand.getId())).isNull();
    }
}
