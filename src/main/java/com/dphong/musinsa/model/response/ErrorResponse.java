package com.dphong.musinsa.model.response;

import com.dphong.musinsa.model.ErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record ErrorResponse<T>(
        @JsonIgnore
        HttpStatus status,
        String code,
        String message
) implements ApiResponse<T> {

    public static <T> ApiResponse<T> internalServerError(ErrorCode errorCode) {
        return new ErrorResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, errorCode.name(), errorCode.getMessage());
    }

    public static <T> ApiResponse<T> internalServerError(String code, String message) {
        return new ErrorResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, code, message);
    }

    public static <T> ApiResponse<T> badRequest(String code, String message) {
        return new ErrorResponse<>(HttpStatus.BAD_REQUEST, code, message);
    }

    public static <T> ApiResponse<T> notFound(String message) {
        return new ErrorResponse<>(HttpStatus.NOT_FOUND, ErrorCode.RESOURCE_NOT_FOUND.name(), message);
    }

    @Override
    public ResponseEntity<?> toResponseEntity() {
        return ResponseEntity.status(status).body(this);
    }
}
