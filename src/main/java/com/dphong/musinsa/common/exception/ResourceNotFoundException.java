package com.dphong.musinsa.common.exception;

public class ResourceNotFoundException extends CustomException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String source, Object identifier) {
        super("%s could not found. id: %s".formatted(source, identifier));
    }
}
