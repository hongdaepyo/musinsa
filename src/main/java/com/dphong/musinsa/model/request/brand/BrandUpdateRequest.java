package com.dphong.musinsa.model.request.brand;

import jakarta.validation.constraints.NotBlank;

public record BrandUpdateRequest(@NotBlank(message = "브랜드 이름이 비어있습니다") String name) {

}
