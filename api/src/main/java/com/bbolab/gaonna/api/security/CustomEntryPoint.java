package com.bbolab.gaonna.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
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
public class CustomEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        log.warn("CustomEntryPoint : 잘못된 토큰으로 페이지 요청");

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        Map<String, String> map = new HashMap<>();
        map.put("errortype", "Forbidden");
        map.put("code", "403");
        map.put("message", "wrong access token. Try login again");

        response.getWriter().write(objectMapper.writeValueAsString(map));
    }
}
