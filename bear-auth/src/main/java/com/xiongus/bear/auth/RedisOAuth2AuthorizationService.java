package com.xiongus.bear.auth;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.util.Assert;

/**
 * RedisOAuth2AuthorizationService.
 *
 * @author xiongus
 */
@Slf4j
public record RedisOAuth2AuthorizationService(
        RedisTemplate<String, Object> redisTemplate) implements OAuth2AuthorizationService {

  private final static Long TIMEOUT = 10L;

  private static final String namespace = "authorization";

  @Override
  public OAuth2Authorization findById(String id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
    Assert.hasText(token, "token cannot be empty");
    Assert.notNull(tokenType, "tokenType cannot be empty");
    return (OAuth2Authorization) redisTemplate.opsForValue().get(buildKey(tokenType.getValue(), token));
  }

  @Override
  public void save(OAuth2Authorization authorization) {
    Assert.notNull(authorization, "authorization cannot be null");
    if (matchesState(authorization)) {
      String token = authorization.getAttribute("state");
      redisTemplate.opsForValue().set(buildKey(OAuth2ParameterNames.STATE, token), authorization, TIMEOUT,
              TimeUnit.MINUTES);
    }
    if (matchesAuthorizationCode(authorization)) {
      OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization
              .getToken(OAuth2AuthorizationCode.class);
      OAuth2AuthorizationCode authorizationCodeToken = authorizationCode.getToken();
      long between = ChronoUnit.MINUTES.between(authorizationCodeToken.getIssuedAt(),
              authorizationCodeToken.getExpiresAt());
      redisTemplate.opsForValue().set(buildKey(OAuth2ParameterNames.CODE, authorizationCodeToken.getTokenValue()),
              authorization, between, TimeUnit.MINUTES);
    }

    if (matchesRefreshToken(authorization)) {
      OAuth2RefreshToken refreshToken = authorization.getRefreshToken().getToken();
      long between = ChronoUnit.SECONDS.between(refreshToken.getIssuedAt(), refreshToken.getExpiresAt());
      redisTemplate.opsForValue().set(buildKey(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken.getTokenValue()),
              authorization, between, TimeUnit.SECONDS);
    }

    if (matchesAccessToken(authorization)) {
      OAuth2AccessToken accessToken = authorization.getAccessToken().getToken();
      long between = ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt());
      redisTemplate.opsForValue().set(buildKey(OAuth2ParameterNames.ACCESS_TOKEN, accessToken.getTokenValue()),
              authorization, between, TimeUnit.SECONDS);
    }
  }

  @Override
  public void remove(OAuth2Authorization authorization) {
    Assert.notNull(authorization, "authorization cannot be null");

    List<String> keys = new ArrayList<>();
    if (matchesState(authorization)) {
      String token = authorization.getAttribute("state");
      keys.add(buildKey(OAuth2ParameterNames.STATE, token));
    }

    if (matchesAuthorizationCode(authorization)) {
      OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization
              .getToken(OAuth2AuthorizationCode.class);
      OAuth2AuthorizationCode authorizationCodeToken = authorizationCode.getToken();
      keys.add(buildKey(OAuth2ParameterNames.CODE, authorizationCodeToken.getTokenValue()));
    }

    if (matchesRefreshToken(authorization)) {
      OAuth2RefreshToken refreshToken = authorization.getRefreshToken().getToken();
      keys.add(buildKey(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken.getTokenValue()));
    }

    if (matchesAccessToken(authorization)) {
      OAuth2AccessToken accessToken = authorization.getAccessToken().getToken();
      keys.add(buildKey(OAuth2ParameterNames.ACCESS_TOKEN, accessToken.getTokenValue()));
    }
    redisTemplate.delete(keys);
  }


  private String buildKey(String type, String id) {
    return String.format("%s:%s:%s", namespace, type, id);
  }

  private static boolean matchesState(OAuth2Authorization authorization) {
    return Objects.nonNull(authorization.getAttribute("state"));
  }

  private static boolean matchesAuthorizationCode(OAuth2Authorization authorization) {
    OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization
            .getToken(OAuth2AuthorizationCode.class);
    return Objects.nonNull(authorizationCode);
  }

  private static boolean matchesAccessToken(OAuth2Authorization authorization) {
    return Objects.nonNull(authorization.getAccessToken());
  }

  private static boolean matchesRefreshToken(OAuth2Authorization authorization) {
    return Objects.nonNull(authorization.getRefreshToken());
  }


}
