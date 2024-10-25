package com.dphong.musinsa.model.response.product;

import com.dphong.musinsa.model.dto.Money;
import java.util.List;

public record ProductsByCategoryResponse(List<ProductResponse> products, Money totalAmount) {

}
