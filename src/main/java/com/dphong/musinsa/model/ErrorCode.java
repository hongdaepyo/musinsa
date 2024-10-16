package com.dphong.musinsa.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR( "내부 서버에러 발생")
    ;

    private final String message;
}
