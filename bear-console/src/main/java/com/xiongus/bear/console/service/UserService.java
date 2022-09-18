package com.xiongus.bear.console.service;

import com.xiongus.bear.console.entity.dto.UserDTO;
import com.xiongus.bear.console.entity.po.User;
import com.xiongus.bear.core.domain.PageDTO;
import org.springframework.data.domain.Pageable;

/**
 * User service
 *
 * @author xiongus
 */
public interface UserService {

  PageDTO<UserDTO> getUsers(String keyword, String role, String status, Pageable pageable);

  UserDTO getUserByUsername(String username);

  User findUserByUsername(String username);

  void updateUserPassword(String username, String password);

  void deleteUserById(long userId);

  void createUser(UserDTO user);

  void updateUser(UserDTO user);
}
