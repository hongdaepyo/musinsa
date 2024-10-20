package com.dphong.musinsa.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.dphong.musinsa.mock.FakeBrandRepository;
import com.dphong.musinsa.model.request.brand.BrandCreateRequest;
import com.dphong.musinsa.model.response.brand.BrandCreateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BrandServiceTest {

    private BrandService brandService;

    @BeforeEach
    void setUp() {
        brandService = new BrandService(new FakeBrandRepository());
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
}
