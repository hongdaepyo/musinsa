package com.dphong.musinsa.service;

import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.model.request.product.ProductCreateRequest;
import com.dphong.musinsa.model.response.product.ProductCreateResponse;
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
            throw new IllegalArgumentException("No brand found with id: " + request.brandId());
        }
        Product product = productRepository.save(
                Product.builder()
                        .name(request.name())
                        .price(request.price())
                        .category(request.category())
                        .brand(brand)
                        .build()
        );
        return new ProductCreateResponse(product.getId(), product.getName());
    }
}
