package com.xiongus.bear.console.controller;

import com.xiongus.bear.console.utils.UserInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
@RequestMapping({"/api/user", "/v1/api/user"})
public class UserController {

  @GetMapping
  @PreAuthorize("hasPermission()")
  public Object hello() {
    return "hello " + UserInfo.getInstance();
  }

}
