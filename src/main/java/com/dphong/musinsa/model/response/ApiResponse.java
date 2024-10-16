package com.dphong.musinsa.model.response;

import org.springframework.http.ResponseEntity;

public interface ApiResponse<T> {
    ResponseEntity<?> toResponseEntity();
}
