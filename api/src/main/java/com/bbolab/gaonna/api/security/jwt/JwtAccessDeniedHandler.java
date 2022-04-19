package com.bbolab.gaonna.api.security.jwt;

import com.bbolab.gaonna.api.security.model.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn("JwtAccessDeniedHandler : User가 ADMIN 권한에 접근 시도");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(ErrorCode.ACCESS_DENIED.getStatusCode());
        JSONObject responseJson = new JSONObject();
        responseJson.put("message", ErrorCode.ACCESS_DENIED.getMessage());
        responseJson.put("code", ErrorCode.ACCESS_DENIED.getErrorCode());

        response.getWriter().print(responseJson);
    }
}
