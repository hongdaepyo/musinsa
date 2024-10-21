package com.dphong.musinsa.common.exception;

public abstract class CustomException extends RuntimeException {

    protected CustomException(String message) {
        super(message);
    }
}
