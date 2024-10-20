package com.dphong.musinsa.model.response.product;

import com.dphong.musinsa.domain.Product;

public record BrandProductResponse(String brandName, int price) {

    public static BrandProductResponse from(Product product) {
        return new BrandProductResponse(product.getBrand().getName(), product.getPrice());
    }

}
