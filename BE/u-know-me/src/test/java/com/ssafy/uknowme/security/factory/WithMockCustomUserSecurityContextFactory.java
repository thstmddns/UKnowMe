package com.ssafy.uknowme.security.factory;

import com.ssafy.uknowme.security.userdetails.WithMockCustomUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;


public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser>  {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser user) {

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.username(), user.password(), Arrays.asList(new SimpleGrantedAuthority(user.role())));

        securityContext.setAuthentication(authenticationToken);

        return securityContext;
    }
}
