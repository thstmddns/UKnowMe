package com.ssafy.uknowme.security.config;

import com.ssafy.uknowme.security.token.AuthTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public AuthTokenProvider authTokenProvider() {
        return new AuthTokenProvider(secret);
    }
}
