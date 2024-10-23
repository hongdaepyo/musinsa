package com.dphong.musinsa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dphong.musinsa.common.exception.ResourceNotFoundException;
import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.domain.ProductCategory;
import com.dphong.musinsa.mock.FakeBrandRepository;
import com.dphong.musinsa.mock.FakeProductRepository;
import com.dphong.musinsa.model.dto.CommonStatus;
import com.dphong.musinsa.model.dto.ProductUpdateStatus;
import com.dphong.musinsa.model.request.product.ProductCreateRequest;
import com.dphong.musinsa.model.request.product.ProductUpdateRequest;
import com.dphong.musinsa.model.response.product.ProductCreateResponse;
import com.dphong.musinsa.model.response.product.ProductDeleteResponse;
import com.dphong.musinsa.model.response.product.ProductUpdateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

    private ProductService service;
    private FakeBrandRepository brandRepository;
    private FakeProductRepository productRepository;

    @BeforeEach
    void setUp() {
        brandRepository = new FakeBrandRepository();
        productRepository = new FakeProductRepository();
        service = new ProductService(productRepository, brandRepository);
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
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 상품을_업데이트한다() {
        // given
        Product product = productRepository.save(
                Product.builder().price(1000).name("product1").category(ProductCategory.TOP).build()
        );
        ProductUpdateRequest request = new ProductUpdateRequest("otherName", ProductCategory.TOP, 1);

        // when
        ProductUpdateResponse response = service.update(product.getId(), request);

        // then
        assertThat(response.status()).isEqualTo(ProductUpdateStatus.SUCCESS);

        Product foundProduct = productRepository.findByIdOrNull(product.getId());
        assertThat(foundProduct.getName()).isEqualTo("otherName");
        assertThat(foundProduct.getCategory()).isEqualTo(ProductCategory.TOP);
        assertThat(foundProduct.getPrice()).isEqualTo(1);
    }

    @Test
    void 상품을_업데이트할_때_상품을_찾을_수_없으면_업데이트할_수_없다() {
        // given
        Product product = productRepository.save(
                Product.builder().price(1000).name("product1").category(ProductCategory.TOP).build()
        );
        ProductUpdateRequest request = new ProductUpdateRequest("otherName", ProductCategory.TOP, 1);
        long invalidProductId = -1L;

        // when
        // then
        assertThatThrownBy(() -> service.update(invalidProductId, request))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 상품을_삭제한다() {
        // given
        Product product = productRepository.save(
                Product.builder().price(1000).name("product1").category(ProductCategory.TOP).build()
        );
        // when
        ProductDeleteResponse response = service.delete(product.getId());

        // then
        assertThat(response.status()).isEqualTo(CommonStatus.SUCCESS);

        Product foundProduct = productRepository.findByIdOrNull(product.getId());
        assertThat(foundProduct).isNull();
    }
}
