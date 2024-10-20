package com.dphong.musinsa.model.response.product;

import com.dphong.musinsa.domain.Product;

public record CategoryProductResponse(String categoryName, String name, int price) {

    public static CategoryProductResponse from(Product product) {
        return new CategoryProductResponse(
                product.getCategory().getDescription(),
                product.getName(),
                product.getPrice()
        );
    }
}
