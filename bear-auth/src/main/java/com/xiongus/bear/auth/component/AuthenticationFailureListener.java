package com.xiongus.bear.auth.component;

import com.xiongus.bear.auth.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationFailureListener
    implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

  private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

  private final RedisTemplate<String, Object> redisTemplate;

  private static final long retry_count = 5;

  @Override
  public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
    String username = event.getAuthentication().getPrincipal().toString();
    long count =
        redisTemplate.opsForValue().increment(Constants.LOGIN_FAILED_COUNT.concat(username));

    String lockCount = (String) redisTemplate.opsForValue().get(Constants.USER_LOCK_COUNT);

    long retry =
        (long) redisTemplate.opsForValue().getAndSet(Constants.USER_LOCK_COUNT, retry_count);
    if (count > retry) {
      throw new LockedException(
          messages.getMessage(
              "AbstractUserDetailsAuthenticationProvider.locked",
              "Login failed " + retry + ",User account is locked"));
    }
    log.error("login failed {}", username);
  }
}
