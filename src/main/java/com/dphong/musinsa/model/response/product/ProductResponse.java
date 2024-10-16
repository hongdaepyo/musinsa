package com.dphong.musinsa.model.response.product;

import com.dphong.musinsa.domain.ProductCategory;

public record ProductResponse(
        ProductCategory category,
        String name,
        String brandName,
        int price
) {

}
