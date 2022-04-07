package com.bbolab.gaonna.api.security.provider;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    private Map<String, Object> userAttributes;

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        userAttributes = (Map<String, Object>)attributes.get("response");
    }

    @Override
    public String getId() {
        return (String) userAttributes.get("id");
    }

    @Override
    public String getName() {
        return (String) userAttributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) userAttributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) userAttributes.get("profile_image");
    }

}
