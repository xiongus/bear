package com.xiongus.bear.console.service.impl;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PermissionsServiceImplTest {

  @Test
  @Order(1)
  public void testGetPermissionByUserId() {
  }

  @Test
  @Order(2)
  public void testGetPermissionsByRoleId() {
  }

  @Test
  @Order(3)
  public void testGetPermissionsByRole() {
  }

  @Test
  @Order(4)
  public void testSavePermission() {

  }

  @Test
  @Order(5)
  public void testDeletePermission() {
  }


}
