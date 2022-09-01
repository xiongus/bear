package com.xiongus.bear.console.service;

import com.xiongus.bear.console.entity.Users;
import com.xiongus.bear.console.request.UserRequest;
import com.xiongus.bear.core.domain.Page;
import java.util.List;

/** UsersService. */
public interface UsersService {

  /**
   * create user.
   *
   * @param username username
   * @param password password
   */
  void createUser(String username, String password);

  /**
   * update password of the user.
   *
   * @param username username
   * @param password password
   */
  void updateUserPassword(String username, String password);

  /**
   * query user by username.
   *
   * @param username username
   * @return user
   */
  Users findUserByUsername(String username);

  /**
   * get users by page.
   *
   * @param request request
   * @return user page info
   */
  Page<Users> getUsers(UserRequest request);

  /**
   * fuzzy query user by username.
   *
   * @param username username
   * @return usernames
   */
  List<String> findUserLikeUsername(String username);

  /**
   * delete user by id
   *
   * @param ids id(,) str
   */
  void deleteUserById(String ids);

  Users getUserById(String id);
}
