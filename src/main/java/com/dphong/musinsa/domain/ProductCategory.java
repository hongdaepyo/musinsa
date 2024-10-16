package com.dphong.musinsa.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductCategory {
    TOP(1),
    OUTERWEAR(2),
    PANTS(3),
    SNEAKERS(4),
    BAG(5),
    HAT(6),
    SOCKS(7);

    private final int order;
}
