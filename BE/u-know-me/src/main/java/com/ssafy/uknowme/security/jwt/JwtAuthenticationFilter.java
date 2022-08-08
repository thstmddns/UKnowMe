package com.ssafy.uknowme.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.uknowme.model.dto.MemberDto.MemberLoginRequestDto;
import com.ssafy.uknowme.security.auth.PrincipalDetails;
import com.ssafy.uknowme.security.exception.DeletedMemberException;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.enums.DeleteState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final String HEADER_STRING = "Authorization";

    private final String AUTHORIZATION_TYPE = "Bearer";

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        MemberLoginRequestDto dto = toMemberRequestDto(request);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getId(), dto.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if (isDeletedMember(authentication)) throw new DeletedMemberException("삭제된 회원입니다.");

        return authentication;
    }

    private boolean isDeletedMember(Authentication authentication) {
        PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();

        Member member = details.getMember();

        return member.getDeleteYn() == DeleteState.Y;
    }

    /**
     * JSON으로 받아온 요청 파라미터를 MemberRequestDto 형태로 변환하는 메서드입니다.
     * @param request HttpServletRequest
     * @return 성공 시 MemberRequestDto, 실패 시 null
     */
    private MemberLoginRequestDto toMemberRequestDto(HttpServletRequest request) {
        try {
            return new ObjectMapper().readValue(request.getInputStream(), MemberLoginRequestDto.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)  {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        Map<String, String> tokenSet = jwtService.createTokenSet(principalDetails.getUsername());

        String accessToken = tokenSet.get("accessToken");
        String refreshToken = tokenSet.get("refreshToken");

        response.addHeader(HEADER_STRING, AUTHORIZATION_TYPE + " " + accessToken);
        response.addHeader("temp", refreshToken);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setMaxAge(14 * 24 * 60 * 60);

        response.addCookie(cookie);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
