package com.bbolab.gaonna.api.security.oauth2;

import com.bbolab.gaonna.api.security.exception.OAuth2ProviderNotMatchingException;
import com.bbolab.gaonna.api.security.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        if (exception instanceof OAuth2ProviderNotMatchingException) {
            setResponse(response, ErrorCode.ALREADY_JOINED_EMAIL);
        }
        else {
            setResponse(response, ErrorCode.UNKNOWN_ERROR);
            log.error("================================================");
            log.error("OAuth2AuthenticationFailureHandler - Unknown 예외 발생");
            log.error("Exception Message : {}", exception.getMessage());
            log.error("Exception StackTrace : {");
            exception.printStackTrace();
            log.error("}");
            log.error("================================================");
        }

        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        JSONObject responseJson = new JSONObject();
        response.setStatus(errorCode.getStatusCode());
        responseJson.put("message", errorCode.getMessage());
        responseJson.put("code", errorCode.getErrorCode());

        response.getWriter().print(responseJson);
    }
}
