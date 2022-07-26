package com.xiongus.bear.market.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/market")
public class IndexController {

  @RequestMapping("")
  public Object getIndex() {
    return "market";
  }
}
