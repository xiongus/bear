package com.xiongus.bear.console.controller;

import com.xiongus.bear.common.model.RestResultUtils;
import com.xiongus.bear.console.request.UserRequest;
import com.xiongus.bear.console.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
   * @param request query param of page
   * @return A collection of users, empty set if no user is found
   */
  @GetMapping
  public Object getUsers(UserRequest request) {
    return usersService.getUsers(request);
  }

  @DeleteMapping("/{id}")
  public Object deleteById(@PathVariable String id) {
    usersService.deleteUserById(id);
    return RestResultUtils.success("delete user ok!");
  }

  @DeleteMapping("/deleteAllById")
  public Object deleteAllById(@RequestParam String ids) {
    usersService.deleteUserById(ids);
    return RestResultUtils.success("delete user ok!");
  }
}
