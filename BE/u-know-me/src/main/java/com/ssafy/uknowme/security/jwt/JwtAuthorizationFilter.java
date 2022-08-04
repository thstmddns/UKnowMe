package com.ssafy.uknowme.security.jwt;

import com.ssafy.uknowme.security.auth.PrincipalDetails;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

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

        String token = request.getHeader("Authorization").replace("Bearer ", "");

        String id = jwtService.getSubject(token);

        if (id != null) {
            Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalStateException("해당 ID의 유저가 없습니다."));

            PrincipalDetails principalDetails = new PrincipalDetails(member);

            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
