package com.dphong.musinsa.model.response.product;

import com.dphong.musinsa.model.dto.Money;

public record ProductResponse(
        String categoryName,
        String name,
        String brandName,
        Money price
) {

}
