package com.dphong.musinsa.controller;

import com.dphong.musinsa.model.request.product.ProductCreateRequest;
import com.dphong.musinsa.model.response.SuccessResponse;
import com.dphong.musinsa.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductCreateController {

    private final ProductService productService;

    @PostMapping
    ResponseEntity<?> create(@Valid @RequestBody ProductCreateRequest request) {
        return SuccessResponse.of(productService.create(request)).toResponseEntity();
    }
}
