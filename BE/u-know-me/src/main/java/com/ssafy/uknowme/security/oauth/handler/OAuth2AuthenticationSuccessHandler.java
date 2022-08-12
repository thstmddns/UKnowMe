package com.ssafy.uknowme.security.oauth.handler;

import com.ssafy.uknowme.security.auth.PrincipalDetails;
import com.ssafy.uknowme.security.properties.AppProperties;
import com.ssafy.uknowme.security.token.AuthToken;
import com.ssafy.uknowme.security.token.AuthTokenProvider;
import com.ssafy.uknowme.security.utils.CookieUtil;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.ssafy.uknowme.security.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REFRESH_TOKEN;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${app.oauth2.authorized-redirect-uris}")
    private String REDIRECT_URL;

    private final AuthTokenProvider authTokenProvider;
    private final AppProperties appProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();

        Member member = details.getMember();
        Role role = member.getRole();

        // 소셜 로그인의 providerId에 해당하는 멤버가 DB에 없는 경우 로그인을 하도록 유도한다.
        String memberId = member.getId();

        if (memberId.equals("empty")) {
            response.getWriter().print(false);
            return;
        }

        AuthToken accessToken = authTokenProvider.createAuthToken(memberId, role.toString(), new Date(System.currentTimeMillis() + appProperties.getAuth().getTokenExpiry()));
        AuthToken refreshToken = authTokenProvider.createAuthToken(appProperties.getAuth().getTokenSecret(), new Date(System.currentTimeMillis() + appProperties.getAuth().getRefreshTokenExpiry()));

        response.addHeader("Authorization", "Bearer " + accessToken.getToken());

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, "access_token", accessToken.getToken(), (int) appProperties.getAuth().getRefreshTokenExpiry());
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), (int) appProperties.getAuth().getRefreshTokenExpiry());

        response.sendRedirect(REDIRECT_URL);
    }

}
