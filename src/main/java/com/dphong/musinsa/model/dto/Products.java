package com.dphong.musinsa.model.dto;

import com.dphong.musinsa.domain.Product;
import java.util.List;

public record Products(List<Product> items) {

    public int getSumOfPrices() {
        return items.stream().mapToInt(Product::getPrice).sum();
    }
}
