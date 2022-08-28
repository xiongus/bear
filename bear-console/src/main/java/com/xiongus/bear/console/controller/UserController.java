package com.xiongus.bear.console.controller;

import com.xiongus.bear.console.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * User related methods entry.
 *
 * @author xiongus
 */
@RestController("/user")
@AllArgsConstructor
@RequestMapping({"/api/user", "/v1/api/user"})
public class UserController {

  private final UsersService usersService;

  /**
   * Get paged users.
   *
   * @param pageNo number index of page
   * @param pageSize size of page
   * @return A collection of users, empty set if no user is found
   */
  @GetMapping
  public Object getUsers(@RequestParam int pageNo, @RequestParam int pageSize) {
    return usersService.getUsers(pageNo, pageSize);
  }
}
