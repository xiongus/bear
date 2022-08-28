package com.xiongus.bear.console.service.impl;

import com.xiongus.bear.console.entity.Permissions;
import com.xiongus.bear.console.entity.Roles;
import com.xiongus.bear.core.domain.Page;
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
public class RolesServiceImplTest {

  @Resource private RolesServiceImpl rolesService;

  @Test
  @Order(1)
  public void testGetRoles() {
    List<Roles> roles = rolesService.getRoles("username");
    Assertions.assertEquals(roles.size(), 0);
  }

  @Test
  @Order(2)
  public void testGetRolesByUserName() {
    Page<Roles> roles = rolesService.getRolesByUserName("username", 1, 10);
    Assertions.assertNotNull(roles);
  }

  @Test
  @Order(3)
  public void testHasPermission() {
    Permissions permissionInfo = new Permissions();
    boolean res = rolesService.hasPermission("username", permissionInfo);
    Assertions.assertFalse(res);
  }

  @Test
  @Order(4)
  public void testAddRole() {
    rolesService.addRole("ROLE_ADMIN", "超级管理员");
  }

  @Test
  @Order(5)
  public void testUpdateRole() {
    rolesService.updateRole("ROLE_ADMIN", "系统超级管理员");
  }

  @Test
  @Order(7)
  public void testDeleteRole() {
    rolesService.deleteRole("ROLE_ADMIN");
  }

  @Test
  @Order(6)
  public void testFindRolesLikeRoleName() {
    List<String> roleNames = rolesService.findRolesLikeRoleName("ROLE_ADMIN");
    Assertions.assertEquals(roleNames.size(), 1);
  }
}
