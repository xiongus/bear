package com.xiongus.bear.auth.web;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiongus.bear.auth.UserInfo;
import com.xiongus.bear.auth.utils.JacksonUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccountController {

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/logout")
  @ResponseBody
  public String logout() {
    return "goodby";
  }

  @GetMapping("/register")
  public String register() {
    return "register";
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

  private String listToStr(Collection<GrantedAuthority> authorities){
    if (CollectionUtils.isEmpty(authorities)) {
      return null;
    }
    List<String> authorityArray = new ArrayList<>(authorities.size());
    for (GrantedAuthority authority : authorities) {
      authorityArray.add(authority.getAuthority());
    }
    return String.join(",",authorityArray);
  }
}
