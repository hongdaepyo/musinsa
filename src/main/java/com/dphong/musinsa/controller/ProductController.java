package com.dphong.musinsa.controller;

import com.dphong.musinsa.model.response.SuccessResponse;
import com.dphong.musinsa.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductQueryService productQueryService;

    @GetMapping
    public ResponseEntity<?> products() {
        return ResponseEntity.ok("Hello World");
    }

    /**
     * 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
     */
    @GetMapping("/lowest")
    public ResponseEntity<?> getProductsWithLowestPriceByCategories() {
        return SuccessResponse.of(productQueryService.getLowestPriceProducts()).toResponseEntity();
    }

    /**
     * 브랜드의 모든 카테고리 최저가 상품을 조회하는 API
     */
    @GetMapping("/lowest/brand/{brandId}")
    public ResponseEntity<?> getProductsWithLowestPriceByCategories(@PathVariable("brandId") Long brandId) {
        return SuccessResponse.of(productQueryService.getLowestPriceProductsByBrand(brandId)).toResponseEntity();
    }
}
