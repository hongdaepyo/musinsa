package com.dphong.musinsa.service;

import com.dphong.musinsa.model.response.product.ProductResponse;
import com.dphong.musinsa.model.response.product.ProductsByCategoryResponse;
import com.dphong.musinsa.repository.product.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductQueryService {

    private final ProductRepository productRepository;

    public ProductsByCategoryResponse getLowestPriceProducts() {
        List<ProductResponse> products = productRepository.findAllLowestPriceProductsByCategory().stream()
                .map(product -> new ProductResponse(
                        product.getCategory(),
                        product.getName(),
                        product.getBrand().getName(),
                        product.getPrice())
                )
                .toList();
        int totalPrice = products.stream().mapToInt(ProductResponse::price).sum();
        return new ProductsByCategoryResponse(products, totalPrice);
    }
}
