package com.dphong.musinsa.controller;

import com.dphong.musinsa.domain.ProductCategory;
import com.dphong.musinsa.model.response.SuccessResponse;
import com.dphong.musinsa.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductSearchController {

    private final ProductQueryService productQueryService;

    /**
     * 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
     */
    @GetMapping("/lowest")
    public ResponseEntity<?> getProductsWithLowestPriceByCategories() {
        return SuccessResponse.of(productQueryService.getLowestPriceProducts()).toResponseEntity();
    }

    /**
     * 모든 카테고리 상품을 구매할 때 최저가인 브랜드의 상품을 조회하는 API
     */
    @GetMapping("/lowest/brand")
    public ResponseEntity<?> getBrandProductsWithLowestPrice() {
        return SuccessResponse.of(productQueryService.getBrandProductsWithLowestPrice()).toResponseEntity();
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
