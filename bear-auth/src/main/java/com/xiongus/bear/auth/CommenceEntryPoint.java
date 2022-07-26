package com.xiongus.bear.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

@Component
public class CommenceEntryPoint implements AuthenticationEntryPoint {

  private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    // send a json object, with http code 401,
    // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    // redirect to login page, for non-ajax request,
//    response.sendRedirect("/login");

    // oauth2/authorize 401 Unauthorized
    redirectStrategy.sendRedirect(request, response, "/login");
  }
}
