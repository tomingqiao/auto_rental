package com.coder.rental.security;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Resource
    private LoginSuccessHandler loginSuccessHandler;
    @Resource
    private LoginFailHandle loginFailHandle;
    @Resource
    private CustomerAccessDeniedHandle customerAccessDeniedHandle;
    @Resource
    private CustomerAnonymousEntryPoint customerAnonymousEntryPoint;
    @Resource
    private CustomerUserDetailsService customerUserDetailsService;
    @Resource
    private VerifyTokenFilter verifyTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        //登陆前过滤
        httpSecurity.addFilterBefore(verifyTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //登录过程处理
        httpSecurity.formLogin()
                .loginProcessingUrl("/rental/user/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailHandle)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/rental/user/login")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customerAnonymousEntryPoint)
                .accessDeniedHandler(customerAccessDeniedHandle)
                .and()
                .cors()
                .and()
                .csrf().disable()
                .userDetailsService(customerUserDetailsService);
        return httpSecurity.build();

    }
}
