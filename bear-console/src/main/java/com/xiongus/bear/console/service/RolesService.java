package com.xiongus.bear.console.service;

import com.xiongus.bear.auth.PermissionInfo;
import com.xiongus.bear.auth.RoleInfo;
import com.xiongus.bear.console.entity.Roles;
import com.xiongus.bear.domain.Page;
import java.util.List;

/** RolesService. */
public interface RolesService {

  /**
   * get roles by list.
   *
   * @param username username
   * @return roles list info
   */
  List<Roles> getRoles(String username);

  /**
   * get roles by page.
   *
   * @param pageNo pageNo
   * @param pageSize pageSize
   * @return roles page info
   */
  Page<RoleInfo> getRolesByUserName(String username, int pageNo, int pageSize);

  /**
   * Determine if the user has permission of the resource.
   *
   * <p>Note if the user has many roles, this method returns true if any one role of the user has
   * the desired permission.
   *
   * @param username user info
   * @param permission permission to auth
   * @return true if granted, false otherwise
   */
  boolean hasPermission(String username, PermissionInfo permission);

  /**
   * Add role.
   *
   * @param role role name
   * @param name role name
   */
  void addRole(String role, String name);

  /**
   * Update role.
   *
   * @param role role name
   * @param name role name
   */
  void updateRole(String role, String name);

  /**
   * delete role.
   *
   * @param role role
   */
  void deleteRole(String role);

  /**
   * fuzzy query roles by role name.
   *
   * @param role role
   * @return roles
   */
  List<String> findRolesLikeRoleName(String role);
}
