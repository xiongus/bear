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
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationLogoutEventListener
    implements ApplicationListener<LogoutSuccessEvent> {

  @Autowired
  private List<LogoutSuccessHandler> handlers;

  @Override
  public void onApplicationEvent(LogoutSuccessEvent event) {
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes();
    HttpServletRequest request = Objects.requireNonNull(requestAttributes).getRequest();
    HttpServletResponse response = requestAttributes.getResponse();
    Authentication authentication = (Authentication) event.getSource();
    if (!CollectionUtils.isEmpty(handlers)) {
      handlers.forEach(handler -> {
        try {
          handler.onLogoutSuccess(request, response, authentication);
        } catch (IOException | ServletException e) {
          throw new RuntimeException(e);
        }
      });
      }
    }
}
