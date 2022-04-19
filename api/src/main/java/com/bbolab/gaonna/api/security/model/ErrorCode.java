package com.bbolab.gaonna.api.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EXPIRED_TOKEN(1001, HttpServletResponse.SC_UNAUTHORIZED,  "만료된 토큰입니다."),
    WRONG_TYPE_TOKEN(1002, HttpServletResponse.SC_UNAUTHORIZED, "변조된 토큰입니다."),
    UNSUPPORTED_TOKEN(1003, HttpServletResponse.SC_UNAUTHORIZED, "변조된 토큰입니다."),
    ACCESS_DENIED(1005, HttpServletResponse.SC_FORBIDDEN, "권한이 없습니다."),
    NEED_AUTHENTICATION(1006, HttpServletResponse.SC_UNAUTHORIZED, "인증이 필요한 요청입니다. 토큰을 확인하세요."),
    ALREADY_JOINED_EMAIL(1007, HttpServletResponse.SC_UNAUTHORIZED, "해당 Email 주소로 이미 가입된 다른 소셜 계정이 있습니다."),
    UNKNOWN_ERROR(9999, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "확인되지 않은 에러입니다.. 연락주세요 ㅜㅜ");

    private int errorCode;
    private int statusCode;
    private String message;


}