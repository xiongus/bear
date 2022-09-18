package com.xiongus.bear.console.service.impl;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RolesServiceImplTest {

  @Test
  @Order(1)
  public void testGetRoles() {
  }

  @Test
  @Order(2)
  public void testGetRolesByUserName() {
  }

  @Test
  @Order(3)
  public void testHasPermission() {
  }

  @Test
  @Order(4)
  public void testAddRole() {
  }

  @Test
  @Order(5)
  public void testUpdateRole() {
  }

  @Test
  @Order(7)
  public void testDeleteRole() {
  }

  @Test
  @Order(6)
  public void testFindRolesLikeRoleName() {
  }
}
