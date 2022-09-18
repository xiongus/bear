package com.xiongus.bear.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

@Slf4j
public class CustomUserDetailsChecker implements UserDetailsChecker {

  private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

  @Override
  public void check(UserDetails user) {
    if (!user.isAccountNonLocked()) {
      log.debug("Failed to authenticate since user account is locked");
      throw new LockedException(
          this.messages.getMessage(
              "AccountStatusUserDetailsChecker.locked", "User account is locked"));
    }
    if (!user.isEnabled()) {
      log.debug("Failed to authenticate since user account is disabled");
      throw new DisabledException(
          this.messages.getMessage("AccountStatusUserDetailsChecker.disabled", "User is disabled"));
    }
    if (!user.isAccountNonExpired()) {
      log.debug("Failed to authenticate since user account is expired");
      throw new AccountExpiredException(
          this.messages.getMessage(
              "AccountStatusUserDetailsChecker.expired", "User account has expired"));
    }
    if (!user.isCredentialsNonExpired()) {
      log.debug("Failed to authenticate since user account credentials have expired");
      throw new CredentialsExpiredException(
          this.messages.getMessage(
              "AccountStatusUserDetailsChecker.credentialsExpired",
              "User credentials have expired"));
    }
  }
}
