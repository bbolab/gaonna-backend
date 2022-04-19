package com.bbolab.gaonna.api.security.jwt;

import com.bbolab.gaonna.api.security.model.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8")
          
        Integer code = (Integer) request.getAttribute("exception");

        if (code != null) {
            if (code == ErrorCode.WRONG_TYPE_TOKEN.getErrorCode()) {
                setResponse(response, ErrorCode.WRONG_TYPE_TOKEN);
            } else if (code == ErrorCode.EXPIRED_TOKEN.getErrorCode()) {
                setResponse(response, ErrorCode.EXPIRED_TOKEN);
            } else if (code == ErrorCode.UNSUPPORTED_TOKEN.getErrorCode()) {
                setResponse(response, ErrorCode.UNSUPPORTED_TOKEN);
            } else {
                setResponse(response, ErrorCode.ACCESS_DENIED);
            }
        }
        else {
            if (authException instanceof InsufficientAuthenticationException) {
                setResponse(response, ErrorCode.NEED_AUTHENTICATION);
            } else {
                setResponse(response, ErrorCode.UNKNOWN_ERROR);
                log.error("================================================");
                log.error("JwtAuthenticationEntryPoint - Unknown 예외 발생");
                log.error("Exception Message : {}", authException.getMessage());
                log.error("Exception StackTrace : {");
                authException.printStackTrace();
                log.error("}");
                log.error("================================================");
            }
        }
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        JSONObject responseJson = new JSONObject();
        response.setStatus(errorCode.getStatusCode());
        responseJson.put("message", errorCode.getMessage());
        responseJson.put("code", errorCode.getErrorCode());
        response.getWriter().print(responseJson);
    }

}
