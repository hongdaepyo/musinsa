package com.dphong.musinsa.controller;

import com.dphong.musinsa.domain.ProductCategory;
import com.dphong.musinsa.model.response.SuccessResponse;
import com.dphong.musinsa.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductQueryService productQueryService;

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

    /**
     * 카테고리 이름으로 최저가와 최고가 상품을 검색하는 API
     */
    @GetMapping("/search-by-category")
    public ResponseEntity<?> getProductsByCategoryName(@RequestParam("name") String categoryName) {
        return SuccessResponse.of(
                productQueryService.getProductsByCategory(ProductCategory.findByDescription(categoryName))
        ).toResponseEntity();
    }
}
