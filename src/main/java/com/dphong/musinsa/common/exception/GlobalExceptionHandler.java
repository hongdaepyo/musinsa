package com.dphong.musinsa.common.exception;

import static com.dphong.musinsa.model.ErrorCode.BAD_REQUEST;
import static com.dphong.musinsa.model.ErrorCode.UNEXPECTED_ERROR;

import com.dphong.musinsa.model.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationError(MethodArgumentNotValidException exception) {
        FieldError error = exception.getBindingResult().getFieldError();
        return ErrorResponse.badRequest(BAD_REQUEST.name(), error.getDefaultMessage()).toResponseEntity();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return ErrorResponse.notFound(exception.getMessage()).toResponseEntity();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(
            HttpServletRequest request,
            RuntimeException exception
    ) {
        return ErrorResponse.internalServerError(UNEXPECTED_ERROR.name(), exception.getMessage()).toResponseEntity();
    }
}
