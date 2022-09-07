package com.xiongus.bear.auth.web;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiongus.bear.auth.UserInfo;
import com.xiongus.bear.auth.utils.JacksonUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

  @GetMapping("/login")
  public ModelAndView login(
      @RequestParam(value = "error", required = false) String error,
      @RequestParam(value = "logout", required = false) String logout,
      HttpServletRequest request) {
    ModelAndView model = new ModelAndView();
    if (error != null) {
      model.addObject("error", getErrorMessage(request));
    }
    if (logout != null) {
      model.addObject("msg", "You have been logged out.");
    }
    model.setViewName("login");
    return model;
  }

  @GetMapping("/login/cover")
  public String loginCover() {
    return "login_cover";
  }

  @GetMapping("/login/illustration")
  public String loginIllustration() {
    return "login_illustration";
  }

  @GetMapping("/register")
  public String register() {
    return "register";
  }

  @GetMapping("/register/cover")
  public String registerCover() {
    return "register_cover";
  }

  @GetMapping("/register/illustration")
  public String registerIllustration() {
    return "register_illustration";
  }

  @GetMapping("/reset")
  public String reset() {
    return "reset";
  }

  @GetMapping("/reset/cover")
  public String resetCover() {
    return "reset_cover";
  }

  @GetMapping("/reset/illustration")
  public String resetIllustration() {
    return "reset_illustration";
  }

  @GetMapping("/verification")
  public String verification() {
    return "verification";
  }

  @GetMapping("/verification/cover")
  public String verificationCover() {
    return "verification_cover";
  }

  @GetMapping("/verification/illustration")
  public String verificationIllustration() {
    return "verification_illustration";
  }

  @GetMapping("/logout")
  @ResponseBody
  public String logout() {
    return "goodly";
  }

  @GetMapping("/home")
  @ResponseBody
  public Object home(Authentication authentication) {
    return "Welcome " + authentication.getName() + "!";
  }

  @GetMapping("/404")
  public String notFound() {
    return "404";
  }

  @GetMapping("/500")
  public String error() {
    return "500";
  }

  @GetMapping("/my-account")
  @ResponseBody
  public Object userinfo(Authentication authentication) {
    ObjectNode result = JacksonUtils.createEmptyJsonNode();
    if (authentication != null) {
      UserInfo userInfo = (UserInfo) authentication.getPrincipal();
      result.put("id", userInfo.getId());
      result.put("username", userInfo.getUsername());
      result.put("displayName", userInfo.getDisplayName());
      result.put("avatar", userInfo.getAvatar());
      result.put("email", userInfo.getEmail());
      result.put("mobileNumber", userInfo.getMobileNumber());
      result.put("address", userInfo.getAddress());
      result.put("authorities", listToStr(userInfo.getAuthorities()));
    }
    return result;
  }

  private String listToStr(Collection<GrantedAuthority> authorities) {
    if (CollectionUtils.isEmpty(authorities)) {
      return null;
    }
    List<String> authorityArray = new ArrayList<>(authorities.size());
    for (GrantedAuthority authority : authorities) {
      authorityArray.add(authority.getAuthority());
    }
    return String.join(",", authorityArray);
  }

  private String getErrorMessage(HttpServletRequest request) {
    Exception exception =
        (Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
    String error = "";
    if (exception instanceof BadCredentialsException) {
      error = "Invalid username and password!";
    } else if (exception instanceof LockedException) {
      error = exception.getMessage();
    } else {
      error = "Invalid username and password!";
    }
    return error;
  }
}
