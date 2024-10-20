package com.dphong.musinsa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.domain.ProductCategory;
import com.dphong.musinsa.mock.FakeBrandRepository;
import com.dphong.musinsa.mock.FakeProductRepository;
import com.dphong.musinsa.model.request.product.ProductCreateRequest;
import com.dphong.musinsa.model.response.product.ProductCreateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

    private ProductService service;
    private FakeBrandRepository brandRepository;

    @BeforeEach
    void setUp() {
        brandRepository = new FakeBrandRepository();
        service = new ProductService(new FakeProductRepository(), brandRepository);
    }

    @Test
    void 상품을_추가한다() {
        // given
        Brand brand = brandRepository.save(Brand.builder().name("testBrand").build());
        ProductCreateRequest request = new ProductCreateRequest("productName", ProductCategory.TOP, 1000, brand.getId());

        // when
        ProductCreateResponse response = service.create(request);

        // then
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("productName");
    }

    @Test
    void 상품을_추가할_때_브랜드_아이디가_유효하지_않으면_상품을_추가할_수_없다() {
        // given
        brandRepository.save(Brand.builder().name("testBrand").build());
        int invalidBrandId = -1;
        ProductCreateRequest request = new ProductCreateRequest("productName", ProductCategory.TOP, 1000, invalidBrandId);

        // when
        // then
        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
