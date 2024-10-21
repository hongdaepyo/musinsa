package com.dphong.musinsa.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR( "내부 서버에러 발생"),
    BAD_REQUEST("잘못된 요청입니다"),
    UNEXPECTED_ERROR("예상치 못한 오류가 발생했습니다"),
    RESOURCE_NOT_FOUND("자원을 찾을 수 없습니다")
    ;

    private final String message;
}
