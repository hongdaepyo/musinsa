package com.dphong.musinsa.model.response.product;

import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.model.dto.Money;

public record CategoryProductResponse(String categoryName, String name, Money price) {

    public static CategoryProductResponse from(Product product) {
        return new CategoryProductResponse(
                product.getCategory().getDescription(),
                product.getName(),
                product.getPrice()
        );
    }
}
