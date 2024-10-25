package com.dphong.musinsa.service;

import com.dphong.musinsa.common.exception.ResourceNotFoundException;
import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.model.dto.CommonStatus;
import com.dphong.musinsa.model.dto.Money;
import com.dphong.musinsa.model.dto.ProductUpdateStatus;
import com.dphong.musinsa.model.request.product.ProductCreateRequest;
import com.dphong.musinsa.model.request.product.ProductUpdateRequest;
import com.dphong.musinsa.model.response.product.ProductCreateResponse;
import com.dphong.musinsa.model.response.product.ProductDeleteResponse;
import com.dphong.musinsa.model.response.product.ProductUpdateResponse;
import com.dphong.musinsa.repository.brand.BrandRepository;
import com.dphong.musinsa.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    @Transactional
    public ProductCreateResponse create(ProductCreateRequest request) {
        Brand brand = brandRepository.findByIdOrNull(request.brandId());
        if (brand == null) {
            throw new ResourceNotFoundException("Brand", request.brandId());
        }
        Product product = productRepository.save(
                Product.builder()
                        .name(request.name())
                        .price(Money.of(request.price()))
                        .category(request.category())
                        .brand(brand)
                        .build()
        );
        return new ProductCreateResponse(product.getId(), product.getName());
    }

    @Transactional
    public ProductUpdateResponse update(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findByIdOrNull(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product", id);
        }
        product.update(request.name(), request.category(), Money.of(request.price()));
        productRepository.save(product);
        return new ProductUpdateResponse(product.getId(), ProductUpdateStatus.SUCCESS);
    }

    @Transactional
    public ProductDeleteResponse delete(Long id) {
        Product product = productRepository.findByIdOrNull(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product", id);
        }
        productRepository.delete(product);
        return new ProductDeleteResponse(id, CommonStatus.SUCCESS);
    }
}
