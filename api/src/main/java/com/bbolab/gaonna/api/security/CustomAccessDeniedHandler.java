package com.bbolab.gaonna.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        log.warn("CustomAccessDeniedHandler : User가 ADMIN 권한에 접근 시도");

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        Map<String, String> map = new HashMap<>();
        map.put("errortype", "Forbidden");
        map.put("code", "403");
        map.put("message", "허용하지 않는 권한에 접근하였습니다");

        response.getWriter().write(objectMapper.writeValueAsString(map));
    }
}
