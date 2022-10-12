package com.xiongus.bear.auth.component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureProviderNotFoundEvent;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationFailureEventListener
    implements ApplicationListener<AbstractAuthenticationFailureEvent> {

  @Autowired(required = false)
  private List<AuthenticationFailureHandler> handlers;

  @Override
  public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
    if (event instanceof AuthenticationFailureProviderNotFoundEvent) {
      return;
    }
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes();
    HttpServletRequest request = Objects.requireNonNull(requestAttributes).getRequest();
    HttpServletResponse response = requestAttributes.getResponse();
    AuthenticationException exception = event.getException();
    if (!CollectionUtils.isEmpty(handlers)) {
      handlers.forEach(failureHandler -> {
        try {
          failureHandler.onAuthenticationFailure(request, response,exception);
        } catch (IOException | ServletException e) {
          throw new RuntimeException(e);
        }
      });
    }
  }
}
