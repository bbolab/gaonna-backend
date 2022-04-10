package com.bbolab.gaonna.api.security.oauth2;

import com.bbolab.gaonna.api.config.SecurityProperties;
import com.bbolab.gaonna.api.security.jwt.JwtTokenProvider;
import com.bbolab.gaonna.api.security.model.JwtToken;
import com.bbolab.gaonna.api.util.CookieUtils;
import com.bbolab.gaonna.api.exception.BadRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;

    private final SecurityProperties securityProperties;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        validateRedirectUri(request, response, authentication);
        if (response.isCommitted()) {
            return;
        }

        JwtToken token = tokenProvider.generateJwtToken(authentication);

        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, String> map = new HashMap<>();
        map.put("access_token", token.getAccessToken());
        map.put("refresh_token", token.getRefreshToken());

        clearAuthenticationAttributes(request, response);
        response.getWriter().write(objectMapper.writeValueAsString(map));
    }

    protected void validateRedirectUri(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent()){
            throw new BadRequestException("We do not support redirection after successful OAuth2 authentication");
        }
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
