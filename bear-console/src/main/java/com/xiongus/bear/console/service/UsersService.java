package com.xiongus.bear.console.service;

import com.xiongus.bear.auth.User;
import com.xiongus.bear.domain.Page;

/**
 * UsersService.
 */
public interface UsersService {

  User findUserByUsername(String username);

  Page<User> getUsers(int pageNo, int pageSize);

  void createUser(String username, String password);
}
