package com.dphong.musinsa.model.response.product;

import java.util.List;

public record BrandLowestPriceProductResponse(
        String brandName,
        List<CategoryProductResponse> products,
        int totalAmount
) {

}
