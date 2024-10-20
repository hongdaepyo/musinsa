package com.dphong.musinsa.model.response.product;

import java.util.List;

public record ProductByCategoryNameResponse(
        String categoryName,
        List<BrandProductResponse> lowestPrice,
        List<BrandProductResponse> highestPrice
) {

}
