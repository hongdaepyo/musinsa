package com.dphong.musinsa.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.ResponseEntity;

public record SuccessResponse<T>(
        String code,
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T data
) implements ApiResponse<T> {

    private static final String SUCCESS_CODE = "SUCCESS";
    private static final String SUCCESS_MESSAGE = "Success";

    public static <T> ApiResponse<T> of(T data) {
        return new SuccessResponse<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    @Override
    public ResponseEntity<?> toResponseEntity() {
        return ResponseEntity.ok(this);
    }
}
