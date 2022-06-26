package com.xiongus.bear.console.service.impl;

import com.xiongus.bear.auth.User;
import com.xiongus.bear.domain.Page;
import java.util.List;
import javax.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersServiceImplTest {

  @Resource private UsersServiceImpl usersService;

  @Test
  @Order(1)
  public void testGetUsers() {
    Page<User> users = usersService.getUsers(1, 10);
    Assertions.assertNotNull(users);
  }

  @Test
  @Order(2)
  public void testCreateUser() {
    usersService.createUser("username", "password");
    String sql = "insert into t_sys_user (id,username,password) values (?, ?, ?)";
    //    Mockito.verify(jdbcTemplate).update(sql,"username", "password");
  }

  @Test
  @Order(3)
  public void testUpdateUserPassword() {
    usersService.updateUserPassword("username", "123456");
    String sql = "update t_sys_user set password=? where username=?";
    //    Mockito.verify(jdbcTemplate).update(sql, "123456", "username");
  }

  @Test
  @Order(4)
  public void testDeleteUser() {
    usersService.deleteUser("username");
    String sql = "delete from t_sys_user where username=?";
    //    Mockito.verify(jdbcTemplate).update(sql, "username");
  }

  @Test
  @Order(5)
  public void testFindUserByUsername() {
    User user = usersService.findUserByUsername("username");
    Assertions.assertNull(user);
  }

  @Test
  @Order(6)
  public void testFindUserLikeUsername() {
    List<String> usernames = usersService.findUserLikeUsername("username");
    Assertions.assertEquals(usernames.size(), 0);
  }
}
