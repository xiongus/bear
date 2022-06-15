package com.xiongus.bear.console.service;

import com.xiongus.bear.auth.PermissionInfo;
import com.xiongus.bear.auth.RoleInfo;
import com.xiongus.bear.console.entity.Roles;
import com.xiongus.bear.domain.Page;
import java.util.List;
import java.util.Optional;

/** RolesService. */
public interface RolesService {
  Optional<List<Roles>> getRoles(String username);

  Page<RoleInfo> getRolesByUserName(String username, int pageNo, int pageSize);

  boolean hasPermission(String username, PermissionInfo permission);
}
