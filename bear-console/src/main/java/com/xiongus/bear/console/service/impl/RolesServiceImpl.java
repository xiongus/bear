package com.xiongus.bear.console.service.impl;

import com.xiongus.bear.common.Constants;
import com.xiongus.bear.console.entity.Permissions;
import com.xiongus.bear.console.entity.Roles;
import com.xiongus.bear.console.repository.RolesRepository;
import com.xiongus.bear.console.repository.RowMapperManager;
import com.xiongus.bear.console.service.PermissionsService;
import com.xiongus.bear.console.service.RolesService;
import com.xiongus.bear.core.distributed.id.IdGeneratorManager;
import com.xiongus.bear.core.distributed.id.ResourceConstants;
import com.xiongus.bear.core.domain.Page;
import com.xiongus.bear.core.domain.PaginationHelper;
import com.xiongus.bear.core.domain.PaginationHelperImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/** RolesServiceImpl. */
@Service
public class RolesServiceImpl implements RolesService {

  @Resource private JdbcTemplate jdbcTemplate;

  @Resource private RolesRepository rolesRepository;

  @Resource private PermissionsService permissionsService;

  @Resource private IdGeneratorManager idGeneratorManager;

  @Override
  public List<Roles> getRoles(String username) {
    return rolesRepository.getRolesByUsername(username);
  }

  @Override
  public Page<Roles> getRolesByUserName(String username, int pageNo, int pageSize) {
    PaginationHelper<Roles> helper = new PaginationHelperImpl<>(jdbcTemplate);
    String sqlCountRows =
        "SELECT count(*) FROM roles r,user_role ur,users u WHERE "
            + " r.id = ur.role_id and ur.user_id = u.id";

    String sqlFetchRows =
        "SELECT r.role,u.username FROM roles r,user_role ur,users u WHERE "
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
  public boolean hasPermission(String username, Permissions permission) {
    // update password
    if (Constants.UPDATE_PASSWORD_ENTRY_POINT.equals(permission.getResource())) {
      return true;
    }

    List<Roles> roles = this.getRoles(username);
    if (CollectionUtils.isEmpty(roles)) {
      return false;
    }

    // Global admin pass:
    for (Roles roleInfo : roles) {
      if (Constants.GLOBAL_ADMIN_ROLE.equals(roleInfo.getRole())) {
        return true;
      }
    }

    // Old global admin can pass resource 'console/':
    if (permission.getResource().startsWith(Constants.CONSOLE_RESOURCE_NAME_PREFIX)) {
      return false;
    }

    // For other roles, use a pattern match to decide if pass or not.
    for (Roles roleInfo : roles) {
      List<Permissions> permissions = permissionsService.getPermissionsByRoleId(roleInfo.getId());
      if (CollectionUtils.isEmpty(permissions)) {
        continue;
      }
      for (Permissions permissionInfo : permissions) {
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

  @Override
  public void addRole(String role, String name) {
    Roles roles = new Roles();
    roles.setId(idGeneratorManager.nextId(ResourceConstants.RESOURCE_ROLE_ID));
    roles.setRole(role);
    roles.setName(name);
    this.rolesRepository.save(roles);
  }

  @Override
  public void updateRole(String role, String name) {
    this.rolesRepository.updateRoleName(role, name);
  }

  @Override
  public void deleteRole(String role) {
    this.rolesRepository.deleteByRole(role);
  }

  @Override
  public List<String> findRolesLikeRoleName(String role) {
    return rolesRepository.findRolesLikeRoleName(role);
  }

  @Override
  public List<Roles> getRoleList() {
    return rolesRepository.findAll();
  }
}
