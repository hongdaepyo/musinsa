package com.dphong.musinsa.controller;

import com.dphong.musinsa.model.request.product.ProductCreateRequest;
import com.dphong.musinsa.model.request.product.ProductUpdateRequest;
import com.dphong.musinsa.model.response.SuccessResponse;
import com.dphong.musinsa.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    ResponseEntity<?> create(@Valid @RequestBody ProductCreateRequest request) {
        return SuccessResponse.of(productService.create(request)).toResponseEntity();
    }

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        return SuccessResponse.of(productService.update(id, request)).toResponseEntity();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        return SuccessResponse.of(productService.delete(id)).toResponseEntity();
    }
}
