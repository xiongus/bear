package com.xiongus.bear.auth.component;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class ForwardLoginEntryPoint implements AuthenticationEntryPoint {

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
    request.getRequestDispatcher("/login").forward(request, response);
  }
}
