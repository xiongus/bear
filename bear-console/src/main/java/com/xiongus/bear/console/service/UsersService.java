package com.xiongus.bear.console.service;

import com.xiongus.bear.console.entity.Users;
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
   * delete user.
   *
   * @param username username
   */
  void deleteUser(String username);

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
   * @param pageNo pageNo
   * @param pageSize pageSize
   * @return user page info
   */
  Page<Users> getUsers(int pageNo, int pageSize);

  /**
   * fuzzy query user by username.
   *
   * @param username username
   * @return usernames
   */
  List<String> findUserLikeUsername(String username);
}
