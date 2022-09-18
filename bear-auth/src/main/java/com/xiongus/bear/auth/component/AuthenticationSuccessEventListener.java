package com.xiongus.bear.auth.component;

import com.xiongus.bear.auth.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationSuccessEventListener
    implements ApplicationListener<AuthenticationSuccessEvent> {

  private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

  private final RedisTemplate<String, Object> redisTemplate;

  @Override
  public void onApplicationEvent(AuthenticationSuccessEvent event) {
    String username = event.getAuthentication().getPrincipal().toString();
    long count =
            redisTemplate.opsForValue().increment(Constants.LOGIN_FAILED_COUNT.concat(username));
    long retry =
            (long) redisTemplate.opsForValue().get(Constants.USER_LOCK_COUNT);
    if (count > retry) {
      throw new LockedException(
              messages.getMessage(
                      "AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"));
    }
    redisTemplate.delete(Constants.LOGIN_FAILED_COUNT.concat(username));
    log.info("login success {}", username);
  }
}
