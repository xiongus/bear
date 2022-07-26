package com.xiongus.bear.auth;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.util.Assert;

/**
 * RedisOAuth2AuthorizationConsentService.
 *
 * @author xiongus
 */
@Slf4j
public record RedisOAuth2AuthorizationConsentService(
        RedisTemplate<String, Object> redisTemplate) implements OAuth2AuthorizationConsentService {

  private static final Long TIMEOUT = 10L;

  private static final String namespace = "consent";

  @Override
  public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
    Assert.hasText(registeredClientId, "registeredClientId cannot be empty");
    Assert.hasText(principalName, "principalName cannot be empty");
    return (OAuth2AuthorizationConsent)
            redisTemplate.opsForValue().get(buildKey(registeredClientId, principalName));
  }

  @Override
  public void save(OAuth2AuthorizationConsent authorizationConsent) {
    Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
    redisTemplate
            .opsForValue()
            .set(buildKey(authorizationConsent), authorizationConsent, TIMEOUT, TimeUnit.MINUTES);
  }

  @Override
  public void remove(OAuth2AuthorizationConsent authorizationConsent) {
    Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
    redisTemplate.delete(buildKey(authorizationConsent));
  }

  private static String buildKey(String registeredClientId, String principalName) {
    return namespace + ":" + registeredClientId + ":" + principalName;
  }

  private static String buildKey(OAuth2AuthorizationConsent authorizationConsent) {
    return buildKey(
            authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
  }
}
