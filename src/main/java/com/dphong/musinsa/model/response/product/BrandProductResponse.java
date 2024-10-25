package com.dphong.musinsa.model.response.product;

import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.model.dto.Money;

public record BrandProductResponse(String brandName, Money price) {

    public static BrandProductResponse from(Product product) {
        return new BrandProductResponse(product.getBrand().getName(), product.getPrice());
    }

}
