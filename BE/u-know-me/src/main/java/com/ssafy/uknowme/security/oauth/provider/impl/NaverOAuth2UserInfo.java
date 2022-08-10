package com.ssafy.uknowme.security.oauth.provider.impl;

import com.ssafy.uknowme.security.oauth.provider.OAuth2UserInfo;

import java.util.Map;

public class NaverOAuth2UserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "NAVER";
    }

    @Override
    public String getMobile() {
        return (String) attributes.get("mobile");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
