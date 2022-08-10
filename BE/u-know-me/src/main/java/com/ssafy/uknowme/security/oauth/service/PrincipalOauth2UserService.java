package com.ssafy.uknowme.security.oauth.service;

import com.ssafy.uknowme.security.auth.PrincipalDetails;
import com.ssafy.uknowme.security.oauth.type.ProviderType;
import com.ssafy.uknowme.security.oauth.provider.OAuth2UserInfo;
import com.ssafy.uknowme.security.oauth.provider.OAuth2UserInfoFactory;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, (Map<String, Object>) user.getAttributes().get("response"));

        String tel = userInfo.getMobile().replaceAll("-", "");

        Member member = memberRepository.findByTel(tel)
                .orElseGet(() -> Member.builder()
                        .id("empty")
                        .password("empty")
                        .build());

        return new PrincipalDetails(member, user.getAttributes());
    }
}
