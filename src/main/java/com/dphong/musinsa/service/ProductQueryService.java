package com.dphong.musinsa.service;

import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.model.response.product.BrandLowestPriceProductResponse;
import com.dphong.musinsa.model.response.product.CategoryProductResponse;
import com.dphong.musinsa.model.response.product.ProductResponse;
import com.dphong.musinsa.model.response.product.ProductsByCategoryResponse;
import com.dphong.musinsa.repository.brand.BrandRepository;
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
    private final BrandRepository brandRepository;

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

    public BrandLowestPriceProductResponse getLowestPriceProductsByBrand(Long brandId) {
        Brand brand = brandRepository.findByIdOrNull(brandId);
        List<Product> products = productRepository.findAllLowestPriceProductsByBrandId(brand.getId());
        List<CategoryProductResponse> categoryProducts = products.stream()
                .map(product -> new CategoryProductResponse(product.getCategory().getDescription(), product.getName(), product.getPrice()))
                .toList();
        int totalPrice = products.stream().mapToInt(Product::getPrice).sum();
        return new BrandLowestPriceProductResponse(brand.getName(), categoryProducts, totalPrice);
    }
}
