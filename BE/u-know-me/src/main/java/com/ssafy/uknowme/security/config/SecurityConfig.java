package com.ssafy.uknowme.security.config;

import com.ssafy.uknowme.security.jwt.JwtAuthenticationFilter;
import com.ssafy.uknowme.security.jwt.JwtAuthorizationFilter;
import com.ssafy.uknowme.security.jwt.JwtService;
import com.ssafy.uknowme.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtService jwtService;

    private final MemberRepository memberRepository;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManager manager = authenticationManagerBuilder.getObject();

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, manager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/member/login");

        JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(jwtService, memberRepository, manager);


        return http
                    .addFilter(corsConfig.corsFilter())
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .addFilter(jwtAuthenticationFilter)
                    .addFilter(jwtAuthorizationFilter)
                    .authorizeRequests()
                    .antMatchers("/member/login", "/member/join", "/member/check/**", "/swagger-ui/**").permitAll()
                .and()
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and().build();
    }
}
