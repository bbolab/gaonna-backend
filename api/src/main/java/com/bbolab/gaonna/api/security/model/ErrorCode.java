package com.bbolab.gaonna.api.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EXPIRED_TOKEN(1001, "만료된 토큰입니다."),
    WRONG_TYPE_TOKEN(1002, "변조된 토큰입니다."),
    UNSUPPORTED_TOKEN(1003, "변조된 토큰입니다."),
    ACCESS_DENIED(1005, "권한이 없습니다."),
    NEED_AUTHENTICATION(1006, "인증이 필요한 요청입니다. 토큰을 확인하세요."),
    UNKNOWN_ERROR(9999, "확인되지 않은 에러입니다.. 연락주세요 ㅜㅜ");

    private int code;
    private String message;


}