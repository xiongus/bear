package com.xiongus.bear.console.service;

import com.xiongus.bear.console.entity.Permissions;
import com.xiongus.bear.core.domain.Page;
import java.util.List;

/** PermissionsService. */
public interface PermissionsService {

  /**
   * get the permissions of user by list.
   *
   * @param userId user
   * @return permissions list info
   */
  List<Permissions> getPermissionByUserId(Long userId);

  /**
   * get the permissions of role by list.
   *
   * @param roleId role
   * @return permissions list info
   */
  List<Permissions> getPermissionsByRoleId(Long roleId);

  /**
   * get the permissions of role by page.
   *
   * @param role role
   * @param pageNo pageNo
   * @param pageSize pageSize
   * @return permissions page info
   */
  Page<Permissions> getPermissionsByRole(String role, int pageNo, int pageSize);

  /**
   * add or update permission.
   *
   * @param permission permission
   */
  void savePermission(Permissions permission);

  /**
   * delete the permission.
   *
   * @param id id
   */
  void deletePermission(Long id);

  /**
   * get the permissions of role by page.
   *
   * @param pageNo pageNo
   * @param pageSize pageSize
   * @return permissions page info
   */
  Page<Permissions> getPermissions(int pageNo, int pageSize);

}
