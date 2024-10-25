package com.dphong.musinsa.model.dto;

import com.dphong.musinsa.domain.Product;
import java.util.List;

public record Products(List<Product> items) {

    public long getSumOfPrices() {
        return items.stream().mapToLong(product -> product.getPrice().amount()).sum();
    }
}
