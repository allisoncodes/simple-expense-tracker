package com.scg.gateway.config;

//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;

import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

//@EnableWebFluxSecurity
public class SecurityConfig {
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(final ServerHttpSecurity http) {
//        return http
//                .authorizeExchange()
//                //.matchers(pathMatchers("/expense-service/**")).authenticated() // require a logged-in user for api calls
//                .anyExchange().authenticated()//permitAll()
//                .and().httpBasic().disable()
//                .csrf().disable()
//                .oauth2ResourceServer()
////                .oauth2Client()
////                .and()
////                .oauth2Login()
//                .and()
//                .build();
//    }
}