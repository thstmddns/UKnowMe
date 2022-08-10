package com.ssafy.uknowme.security.oauth.provider;

public interface OAuth2UserInfo {

    String getProviderId();

    String getProvider();

    String getMobile();

    String getName();
}
