package com.bbolab.gaonna.api.security.model.provider;

import com.bbolab.gaonna.api.exception.OAuth2AuthenticationProcessingException;
import com.bbolab.gaonna.core.domain.member.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.naver.toString())) {
            return new NaverOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException(registrationId + " 로그인은 지원하지 않습니다.");
        }
    }
}
