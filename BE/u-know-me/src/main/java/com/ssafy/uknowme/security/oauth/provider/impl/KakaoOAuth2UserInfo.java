package com.ssafy.uknowme.security.oauth.provider.impl;

import com.ssafy.uknowme.security.oauth.provider.OAuth2UserInfo;

import java.util.Map;

public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;
    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "KAKAO";
    }

    @Override
    public String getBirthday() {
        return (String) attributes.get("birthday");
    }

    @Override
    public String getMobile() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
