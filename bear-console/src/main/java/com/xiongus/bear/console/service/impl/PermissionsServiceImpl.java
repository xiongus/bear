package com.xiongus.bear.console.service.impl;

import com.xiongus.bear.auth.PermissionInfo;
import com.xiongus.bear.console.entity.Permissions;
import com.xiongus.bear.console.repository.PermissionsRepository;
import com.xiongus.bear.console.repository.RowMapperManager;
import com.xiongus.bear.console.service.PermissionsService;
import com.xiongus.bear.core.distributed.id.IdGeneratorManager;
import com.xiongus.bear.core.distributed.id.ResourceConstants;
import com.xiongus.bear.domain.Page;
import com.xiongus.bear.domain.PaginationHelper;
import com.xiongus.bear.domain.PaginationHelperImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/** PermissionsServiceImpl. */
@Service
public class PermissionsServiceImpl implements PermissionsService {

  @Resource private JdbcTemplate jdbcTemplate;
  @Resource private PermissionsRepository permissionsRepository;
  @Resource private IdGeneratorManager idGeneratorManager;

  @Override
  public List<Permissions> getPermissionByUserId(Long userId) {
    return permissionsRepository.findPermissionByUserId(userId);
  }

  @Override
  public List<Permissions> getPermissionsByRoleId(Long roleId) {
    return permissionsRepository.getPermissionsByRoleId(roleId);
  }

  @Override
  public Page<PermissionInfo> getPermissionsByRole(String role, int pageNo, int pageSize) {
    PaginationHelper<PermissionInfo> helper = new PaginationHelperImpl<>(jdbcTemplate);

    String sqlCountRows =
        "SELECT count(*) FROM permissions p,role_permission rp,roles r "
            + "WHERE p.id = rp.permission_id and rp.role_id = r.id";

    String sqlFetchRows =
        "SELECT r.role,p.resource,p.action "
            + "FROM permissions p,role_permission rp,roles r "
            + "WHERE p.id = rp.permission_id and rp.role_id = r.id";

    String where = " and r.role= ? ";
    List<String> params = new ArrayList<>();
    if (StringUtils.isNotBlank(role)) {
      params = Collections.singletonList(role);
    } else {
      where = " and 1=1 ";
    }

    return helper.fetchPage(
        sqlCountRows + where,
        sqlFetchRows + where,
        params.toArray(),
        pageNo,
        pageSize,
        RowMapperManager.PERMISSION_ROW_MAPPER);
  }

  @Override
  public void savePermission(Permissions permission) {
    if (permission.getId() == null) {
      permission.setId(idGeneratorManager.nextId(ResourceConstants.RESOURCE_PERMISSION_ID));
    }
    this.permissionsRepository.save(permission);
  }

  @Override
  public void deletePermission(Long id) {
    this.permissionsRepository.deleteById(id);
  }
}
