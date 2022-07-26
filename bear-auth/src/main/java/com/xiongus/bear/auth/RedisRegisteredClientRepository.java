package com.xiongus.bear.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.Assert;

/**
 * CustomRegisteredClientRepository.
 *
 * @author xiongus
 */
@Slf4j
public class RedisRegisteredClientRepository extends JdbcRegisteredClientRepository {

  private final RedisTemplate<String, Object> redisTemplate;

  private static final String namespace = "registered_client";

  public RedisRegisteredClientRepository(
      JdbcTemplate jdbcTemplate, RedisTemplate<String, Object> redisTemplate) {
    super(jdbcTemplate);
    this.redisTemplate = redisTemplate;
  }

  @Override
  public RegisteredClient findById(String id) {
    Assert.hasText(id, "id cannot be empty");
    return super.findById(id);
  }

  @Override
  public RegisteredClient findByClientId(String clientId) {
    Assert.hasText(clientId, "clientId cannot be empty");
    return super.findByClientId(clientId);
  }
}
