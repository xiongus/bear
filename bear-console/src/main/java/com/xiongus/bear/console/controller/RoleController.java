package com.xiongus.bear.console.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/role")
@RequestMapping({"/api/role", "/v1/api/role"})
public class RoleController {

  @GetMapping
  public Object hello() {
    return "hello world!";
  }
}
