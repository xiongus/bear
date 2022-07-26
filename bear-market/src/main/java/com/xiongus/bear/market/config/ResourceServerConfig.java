package com.xiongus.bear.market.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ResourceServerConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.mvcMatcher("/market/**")
                .authorizeRequests()
                .mvcMatchers("/market/**")
                .access("hasAuthority('SCOPE_market')")
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }
}
