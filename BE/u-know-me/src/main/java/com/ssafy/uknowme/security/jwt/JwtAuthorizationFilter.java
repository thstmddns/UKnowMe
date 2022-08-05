package com.ssafy.uknowme.security.jwt;

import com.ssafy.uknowme.security.auth.PrincipalDetails;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.repository.MemberRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final String HEADER_STRING = "Authorization";

    private final String AUTHORIZATION_TYPE = "Bearer";

    private final JwtService jwtService;

    private final MemberRepository memberRepository;

    public JwtAuthorizationFilter(JwtService jwtService, MemberRepository memberRepository, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.jwtService = jwtService;
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String jwtHeader = request.getHeader("Authorization");

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        String accessToken = request.getHeader("Authorization").replace("Bearer ", "");

        String id;

        try {
            id = jwtService.getSubject(accessToken);
        } catch (ExpiredJwtException e) {
            Cookie[] cookies = request.getCookies();

            String refreshToken = getRefreshTokenByCookie(cookies);

            System.out.println(refreshToken);

            // 리프레쉬 토큰도 없는 경우 리턴
            if (refreshToken == null) return;

            id = jwtService.getSubject(refreshToken);

            if (id == null) return;

            String createdAccessToken = jwtService.getAccessToken(id);

            response.addHeader(HEADER_STRING, AUTHORIZATION_TYPE + " " + createdAccessToken);

            chain.doFilter(request, response);
        }

        // id에 아무 값이 들어 있지 않으면 리턴
        if (id == null) return;

        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalStateException("해당 ID의 유저가 없습니다."));

        PrincipalDetails principalDetails = new PrincipalDetails(member);

        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    private String getRefreshTokenByCookie(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
