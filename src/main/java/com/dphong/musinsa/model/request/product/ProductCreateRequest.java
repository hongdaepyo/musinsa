package com.dphong.musinsa.model.request.product;

import com.dphong.musinsa.domain.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductCreateRequest(
        @NotBlank(message = "상품 이름이 비어있습니다") String name,
        ProductCategory category,
        @PositiveOrZero(message = "가격은 0 이상이어야 합니다") long price,
        long brandId
) {

}
