package com.dphong.musinsa.model.response.product;

import com.dphong.musinsa.model.dto.Money;
import java.util.List;

public record BrandLowestPriceProductResponse(
        String brandName,
        List<CategoryProductResponse> categories,
        Money totalAmount
) {

}
