package com.dphong.musinsa.domain;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductCategory {
    TOP(1, "상의"),
    OUTERWEAR(2, "아우터"),
    PANTS(3, "바지"),
    SNEAKERS(4, "스니커즈"),
    BAG(5, "가방"),
    HAT(6, "모자"),
    SOCKS(7, "양말"),
    ACCESSORY(8, "액세서리"),
    ;

    private final int order;
    private final String description;

    public static ProductCategory findByDescription(String description) {
        return Arrays.stream(values())
                .filter(it -> it.description.equals(description))
                .findFirst()
                .orElseThrow();
    }
}
