package com.xiongus.bear.compoment;

import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

  @Autowired private RedisTemplate<String, Object> redisTemplate;

  @Override
  public Mono<AuthorizationDecision> check(
      Mono<Authentication> authentication, AuthorizationContext object) {
    // 从Redis中获取当前路径可访问角色列表
    URI uri = object.getExchange().getRequest().getURI();
    List<String> authorities = (List<String>) redisTemplate.opsForHash().get("", uri.getPath());
    // 认证通过且角色匹配的用户可访问当前路径
    return authentication
        .filter(Authentication::isAuthenticated)
        .flatMapIterable(Authentication::getAuthorities)
        .map(GrantedAuthority::getAuthority)
        .any(authorities::contains)
        .map(AuthorizationDecision::new)
        .defaultIfEmpty(new AuthorizationDecision(false));
  }
}
