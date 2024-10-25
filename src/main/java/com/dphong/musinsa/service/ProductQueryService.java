package com.dphong.musinsa.service;

import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.domain.ProductCategory;
import com.dphong.musinsa.model.dto.Money;
import com.dphong.musinsa.model.dto.Products;
import com.dphong.musinsa.model.response.product.*;
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
                        product.getCategory().getDescription(),
                        product.getName(),
                        product.getBrand().getName(),
                        product.getPrice())
                )
                .toList();
        long totalPrice = products.stream().mapToLong(product -> product.price().amount()).sum();
        return new ProductsByCategoryResponse(products, new Money(totalPrice));
    }

    public BrandProductWithLowestSumOfPricesResponse getBrandProductsWithLowestPrice() {
        Brand brand = brandRepository.findBrandWithSumOfLowestPrices();
        List<Product> products = brand.getProducts();
        List<CategoryProductResponse> categoryProducts = products.stream().map(CategoryProductResponse::from).toList();
        long totalPrice = new Products(products).getSumOfPrices();
        return new BrandProductWithLowestSumOfPricesResponse(
                new BrandLowestPriceProductResponse(brand.getName(), categoryProducts, Money.of(totalPrice))
        );
    }

    public ProductByCategoryNameResponse getProductsByCategory(ProductCategory category) {
        Product lowestPriceProduct = productRepository.findLowestPriceProductByCategory(category);
        Product highestPriceProduct = productRepository.findHighestPriceProductByCategory(category);
        return new ProductByCategoryNameResponse(
                category.getDescription(),
                List.of(BrandProductResponse.from(lowestPriceProduct)),
                List.of(BrandProductResponse.from(highestPriceProduct))
        );
    }
}
