package com.xiongus.bear.console.service.impl;

import com.xiongus.bear.auth.PermissionInfo;
import com.xiongus.bear.console.entity.Permissions;
import com.xiongus.bear.domain.Page;
import java.time.LocalDateTime;
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
public class PermissionsServiceImplTest {

  @Resource private PermissionsServiceImpl permissionsService;

  @Test
  @Order(1)
  public void testGetPermissionByUserId() {
    List<Permissions> permissions = permissionsService.getPermissionByUserId(1L);
    Assertions.assertEquals(permissions.size(), 0);
  }

  @Test
  @Order(2)
  public void testGetPermissionsByRoleId() {
    List<Permissions> permissions = permissionsService.getPermissionsByRoleId(1L);
    Assertions.assertEquals(permissions.size(), 0);
  }

  @Test
  @Order(3)
  public void testGetPermissionsByRole() {
    Page<PermissionInfo> permissions = permissionsService.getPermissionsByRole("ROLE_ADMIN", 1, 10);
    Assertions.assertNotNull(permissions);
  }

  @Test
  @Order(4)
  public void testSavePermission() {
    Permissions permissions = new Permissions();
    permissions.setId(1L);
    permissions.setName("系统管理");
    permissions.setAction("/");
    permissions.setResource("/system");
    permissions.setResourceType("Read");
    permissionsService.savePermission(permissions);

    // Hibernate: update t_sys_permission set deleted=?, name=?, parent_id=? where id=?
    // Error: 1048-23000: Column 'deleted' cannot be null
    permissions.setName("系统管理2");
    permissions.setDeleted(false);
    permissions.setCreateBy("1");
    permissions.setCreateTime(LocalDateTime.now());
    permissions.setModifyBy("2");
    permissions.setModifyTime(LocalDateTime.now());
    permissions.setParentId(0L);
    permissionsService.savePermission(permissions);
  }

  @Test
  @Order(5)
  public void testDeletePermission() {
    permissionsService.deletePermission(1L);
  }


}
