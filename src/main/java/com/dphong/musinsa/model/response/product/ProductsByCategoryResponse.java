package com.dphong.musinsa.model.response.product;

import java.util.List;

public record ProductsByCategoryResponse(List<ProductResponse> products, int totalAmount) {


}
