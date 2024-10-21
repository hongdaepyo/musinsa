package com.dphong.musinsa.fixtures;

import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.domain.Brand.BrandBuilder;

public class BrandFixture {

    public static BrandBuilder create() {
        return Brand.builder().name("testBrand");
    }
}
