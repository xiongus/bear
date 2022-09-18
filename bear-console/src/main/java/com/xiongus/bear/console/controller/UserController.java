package com.xiongus.bear.console.controller;

import com.google.common.base.Preconditions;
import com.xiongus.bear.common.model.RestResultUtils;
import com.xiongus.bear.console.entity.dto.UserDTO;
import com.xiongus.bear.console.entity.po.User;
import com.xiongus.bear.console.service.UserService;
import com.xiongus.bear.console.utils.PasswordEncoderUtil;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * User related methods entry.
 *
 * @author xiongus
 */
@Validated
@RestController
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/v1/users")
  public Object getUsers(
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) String role,
      @RequestParam(required = false) String status,
      Pageable pageable) {
    return userService.getUsers(keyword, role, status, pageable);
  }

  @GetMapping("/v1/user/{username}")
  public Object getUserByUsername(@PathVariable String username) {
    return userService.getUserByUsername(username);
  }

  @PostMapping("/v1/user")
  public Object createUser(@RequestBody @Valid UserDTO user) {
    UserDTO dto = userService.getUserByUsername(user.getUsername());
    Preconditions.checkState(dto != null, "User %s already exists!", user.getUsername());
    userService.createUser(user);
    return RestResultUtils.success("create user ok!");
  }

  @PutMapping("/v1/user")
  public Object modifyUser(@RequestBody UserDTO user) {
    UserDTO dto = userService.getUserByUsername(user.getUsername());
    Preconditions.checkState(dto == null, "User %s doesn't exist!", user.getUsername());
    userService.updateUser(user);
    return RestResultUtils.success("update user ok!");
  }

  @PutMapping("/v1/user/{username}/password")
  public Object modifyPassword(
      @RequestParam(value = "oldPassword") String oldPassword,
      @RequestParam(value = "newPassword") String newPassword,
      @PathVariable String username) {
    User user = userService.findUserByUsername(username);
    Preconditions.checkState(user == null, "User %s doesn't exists!", username);
    String password = user.getPassword();
    try {
      if (PasswordEncoderUtil.matches(oldPassword, password)) {
        userService.updateUserPassword(username, PasswordEncoderUtil.encode(newPassword));
        return RestResultUtils.success("Update password success");
      }
      return RestResultUtils.failed(HttpStatus.UNAUTHORIZED.value(), "Old password is invalid");
    } catch (Exception e) {
      return RestResultUtils.failed(
          HttpStatus.INTERNAL_SERVER_ERROR.value(), "Update user password failed");
    }
  }

  @DeleteMapping("/v1/user/{username}")
  public Object deleteUser(@PathVariable String username) {
    User user = userService.findUserByUsername(username);
    Preconditions.checkState(user == null, "User %s doesn't exists!", username);
    // todo check role is admin ?
    userService.deleteUserById(user.getId());
    return RestResultUtils.success("delete user ok!");
  }

  @DeleteMapping("/v1/users")
  public Object deleteUsers(@RequestParam List<String> usernames) {
    usernames.forEach(this::deleteUser);
    return RestResultUtils.success("delete users ok!");
  }

}
