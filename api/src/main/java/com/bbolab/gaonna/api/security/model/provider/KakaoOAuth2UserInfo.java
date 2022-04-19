package com.bbolab.gaonna.api.security.model.provider;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo{

    Map<String, Object> properties;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super((Map<String, Object>) attributes.get("kakao_account"));
        properties = (Map<String, Object>) attributes.get("properties");
    }

    @Override
    public String getId() {
        return null; // "id" field is not supported by kakao
    }

    @Override
    public String getName() {
        return (String) properties.get("nickname"); // "name" filed is not supported by kakao
    }

    @Override
    public String getNickname() {
        return (String) properties.get("nickname");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) properties.get("profile_image");
    }

}
