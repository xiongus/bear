package com.xiongus.bear.console.service;

import com.xiongus.bear.auth.PermissionInfo;
import com.xiongus.bear.console.entity.Permissions;
import com.xiongus.bear.domain.Page;
import java.util.List;
import java.util.Optional;

/**
 * PermissionsService.
 */
public interface PermissionsService {

  Optional<List<Permissions>> getPermissionByUserId(Long userId);

  Optional<List<Permissions>> getPermissionsByRoleId(Long roleId);

  Page<PermissionInfo> getPermissionsByRole(String role, int pageNo, int pageSize);
}
