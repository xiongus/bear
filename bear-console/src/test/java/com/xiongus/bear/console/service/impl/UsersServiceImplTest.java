package com.xiongus.bear.console.service.impl;

import com.xiongus.bear.console.entity.dto.UserDTO;
import com.xiongus.bear.console.service.UserService;
import com.xiongus.bear.core.domain.PageDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersServiceImplTest {

  @Autowired private UserService userService;

  @Test
  @Order(1)
  public void testGetUsers() {
    PageDTO<UserDTO> users = userService.getUsers("", "", "", PageRequest.of(1, 10));
    System.out.println(users);
  }

  @Test
  @Order(2)
  public void testCreateUser() {}

  @Test
  @Order(3)
  public void testUpdateUserPassword() {}

  @Test
  @Order(4)
  public void testDeleteUser() {}

  @Test
  @Order(5)
  public void testFindUserByUsername() {}

  @Test
  @Order(6)
  public void testFindUserLikeUsername() {}
}
