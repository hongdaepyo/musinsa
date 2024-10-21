package com.dphong.musinsa.model.response.product;

public record ProductResponse(
        String categoryName,
        String name,
        String brandName,
        int price
) {

}
