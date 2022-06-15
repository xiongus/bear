package com.xiongus.bear.console.service.impl;

import com.xiongus.bear.auth.AuthConstants;
import com.xiongus.bear.auth.PermissionInfo;
import com.xiongus.bear.auth.RoleInfo;
import com.xiongus.bear.common.Constants;
import com.xiongus.bear.console.entity.Permissions;
import com.xiongus.bear.console.entity.Roles;
import com.xiongus.bear.console.repository.RolesRepository;
import com.xiongus.bear.console.repository.RowMapperManager;
import com.xiongus.bear.console.service.PermissionsService;
import com.xiongus.bear.console.service.RolesService;
import com.xiongus.bear.domain.Page;
import com.xiongus.bear.domain.PaginationHelper;
import com.xiongus.bear.domain.PaginationHelperImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.util.ConcurrentHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * RolesServiceImpl.
 */
@Service
public class RolesServiceImpl implements RolesService {

  private static final Logger LOGGER = LoggerFactory.getLogger(RolesServiceImpl.class);

  @Resource private JdbcTemplate jdbcTemplate;

  @Resource private RolesRepository rolesRepository;

  @Resource private PermissionsService permissionsService;

  private volatile Set<String> roleSet = new ConcurrentHashSet<>();

  private volatile Map<String, List<RoleInfo>> roleInfoMap = new ConcurrentHashMap<>();

  private volatile Map<String, List<PermissionInfo>> permissionInfoMap = new ConcurrentHashMap<>();

  @Scheduled(initialDelay = 5000, fixedDelay = 15000)
  private void reload() {
    try {
      Page<RoleInfo> roleInfoPage =
          this.getRolesByUserName(StringUtils.EMPTY, Constants.DEFAULT_PAGE_NO, Integer.MAX_VALUE);
      if (roleInfoPage == null) {
        return;
      }
      Set<String> tmpRoleSet = new HashSet<>(16);
      Map<String, List<RoleInfo>> tmpRoleInfoMap = new ConcurrentHashMap<>(16);
      for (RoleInfo roleInfo : roleInfoPage.getPageItems()) {
        if (!tmpRoleInfoMap.containsKey(roleInfo.getUsername())) {
          tmpRoleInfoMap.put(roleInfo.getUsername(), new ArrayList<>());
        }
        tmpRoleInfoMap.get(roleInfo.getUsername()).add(roleInfo);
        tmpRoleSet.add(roleInfo.getRole());
      }

      Map<String, List<PermissionInfo>> tmpPermissionInfoMap = new ConcurrentHashMap<>(16);
      for (String role : tmpRoleSet) {
        Page<PermissionInfo> permissionInfoPage =
            permissionsService.getPermissionsByRole(
                role, Constants.DEFAULT_PAGE_NO, Integer.MAX_VALUE);
        tmpPermissionInfoMap.put(role, permissionInfoPage.getPageItems());
      }

      roleSet = tmpRoleSet;
      roleInfoMap = tmpRoleInfoMap;
      permissionInfoMap = tmpPermissionInfoMap;
    } catch (Exception e) {
      LOGGER.warn("[LOAD-ROLES] load failed", e);
    }
  }

  @Override
  public Optional<List<Roles>> getRoles(String username) {
    return rolesRepository.getRolesByUsername(username);
  }

  @Override
  public Page<RoleInfo> getRolesByUserName(String username, int pageNo, int pageSize) {
    PaginationHelper<RoleInfo> helper = new PaginationHelperImpl<RoleInfo>(jdbcTemplate);
    String sqlCountRows =
        "SELECT count(*) FROM t_sys_role r,t_sys_user_role ur,t_sys_user u WHERE "
            + " r.id = ur.role_id and ur.user_id = u.id";

    String sqlFetchRows =
        "SELECT r.role,u.username FROM t_sys_role r,t_sys_user_role ur,t_sys_user u WHERE "
            + " r.id = ur.role_id and ur.user_id = u.id";

    String where = " and u.username= ? ";
    List<String> params = new ArrayList<>();
    if (StringUtils.isNotBlank(username)) {
      params = Collections.singletonList(username);
    } else {
      where = " and 1=1 ";
    }
    return helper.fetchPage(
        sqlCountRows + where,
        sqlFetchRows + where,
        params.toArray(),
        pageNo,
        pageSize,
        RowMapperManager.ROLE_INFO_ROW_MAPPER);
  }

  @Override
  public boolean hasPermission(String username, PermissionInfo permission) {
    // update password
    if (AuthConstants.UPDATE_PASSWORD_ENTRY_POINT.equals(permission.getResource())) {
      return true;
    }

    Optional<List<Roles>> optionalRoles = this.getRoles(username);
    if (optionalRoles.isEmpty()) {
      return false;
    }

    // Global admin pass:
    for (Roles roleInfo : optionalRoles.get()) {
      if (Constants.GLOBAL_ADMIN_ROLE.equals(roleInfo.getRole())) {
        return true;
      }
    }

    // Old global admin can pass resource 'console/':
    if (permission.getResource().startsWith(AuthConstants.CONSOLE_RESOURCE_NAME_PREFIX)) {
      return false;
    }

    // For other roles, use a pattern match to decide if pass or not.
    for (Roles roleInfo : optionalRoles.get()) {
      Optional<List<Permissions>> optionalPermissions =
          permissionsService.getPermissionsByRoleId(roleInfo.getId());
      if (optionalPermissions.isEmpty()) {
        continue;
      }
      for (Permissions permissionInfo : optionalPermissions.get()) {
        String permissionResource = permissionInfo.getResource().replaceAll("\\*", ".*");
        String permissionAction = permissionInfo.getAction();
        if (permissionAction.contains(permission.getAction())
            && Pattern.matches(permissionResource, permission.getResource())) {
          return true;
        }
      }
    }
    return false;
  }
}
