package com.xiongus.bear.compoment.config;

import reactor.core.publisher.Mono;

import com.xiongus.bear.compoment.AuthorizationManager;
import com.xiongus.bear.compoment.CustomServerAccessDeniedHandler;
import com.xiongus.bear.compoment.CustomServerAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;


@AllArgsConstructor
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class ResourceServerConfig {

    private final IgnoreUrlsConfig ignoreUrlsConfig;

    private final AuthorizationManager authorizationManager;

    private final CustomServerAccessDeniedHandler customServerAccessDeniedHandler;

    private final CustomServerAuthenticationEntryPoint customServerAuthenticationEntryPoint;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.oauth2ResourceServer().jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());

        http.authorizeExchange()
                .pathMatchers(ignoreUrlsConfig.getUrls())
                    .permitAll()
                .anyExchange().access(authorizationManager)
                .and().exceptionHandling()
                .accessDeniedHandler(customServerAccessDeniedHandler)
                .authenticationEntryPoint(customServerAuthenticationEntryPoint)
                .and().csrf().disable();
        return http.build();
    }

    private static final String AUTHORITY_PREFIX = "ROLE_";

    private static final String AUTHORITY_CLAIM_NAME = "authorities";

    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AUTHORITY_CLAIM_NAME);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}
