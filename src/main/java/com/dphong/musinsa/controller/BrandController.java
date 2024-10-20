package com.dphong.musinsa.controller;

import com.dphong.musinsa.model.request.brand.BrandCreateRequest;
import com.dphong.musinsa.model.response.SuccessResponse;
import com.dphong.musinsa.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/brands")
@RestController
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BrandCreateRequest request) {
        return SuccessResponse.of(brandService.create(request)).toResponseEntity();
    }

}
