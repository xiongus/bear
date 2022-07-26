package com.xiongus.bear.auth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.xiongus.bear.auth.CommenceEntryPoint;
import com.xiongus.bear.auth.RedisOAuth2AuthorizationConsentService;
import com.xiongus.bear.auth.RedisOAuth2AuthorizationService;
import com.xiongus.bear.auth.RedisRegisteredClientRepository;
import com.xiongus.bear.auth.jose.Jwks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * AuthorizationServerConfig.
 *
 * @author xiongus
 */
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

  private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";

  @Value("${provider.settings.issuer:http://localhost:9000}")
  private String providerSettingsIssuer;

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
      throws Exception {
    OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer =
        new OAuth2AuthorizationServerConfigurer<>();
    authorizationServerConfigurer.authorizationEndpoint(
        authorizationEndpoint -> authorizationEndpoint.consentPage(CUSTOM_CONSENT_PAGE_URI));

    RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

    http.requestMatcher(endpointsMatcher)
        .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
        .exceptionHandling()
        .authenticationEntryPoint(new CommenceEntryPoint()).and()
        .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
        .apply(authorizationServerConfigurer);
    return http.build();
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    RSAKey rsaKey = Jwks.generateRsa();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
  }

  @Bean
  public ProviderSettings providerSettings() {
    return ProviderSettings.builder().issuer(providerSettingsIssuer).build();
  }

  @Bean
  public RegisteredClientRepository registeredClientRepository(
      JdbcTemplate jdbcTemplate, RedisTemplate<String, Object> redisTemplate) {
    // org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql
    return new RedisRegisteredClientRepository(jdbcTemplate, redisTemplate);
  }

  @Bean
  public OAuth2AuthorizationConsentService authorizationConsentService(
      RedisTemplate<String, Object> redisTemplate) {
    // Will be used by the ConsentController
    // org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql
    return new RedisOAuth2AuthorizationConsentService(redisTemplate);
  }

  @Bean
  public OAuth2AuthorizationService authorizationService(
      RedisTemplate<String, Object> redisTemplate) {
    // org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql
    return new RedisOAuth2AuthorizationService(redisTemplate);
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(RedisSerializer.string());
    redisTemplate.setHashKeySerializer(RedisSerializer.string());
    redisTemplate.setValueSerializer(RedisSerializer.java());
    redisTemplate.setHashValueSerializer(RedisSerializer.java());
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }
}
