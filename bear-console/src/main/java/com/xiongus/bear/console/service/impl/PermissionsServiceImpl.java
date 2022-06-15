package com.xiongus.bear.console.service.impl;

import com.xiongus.bear.auth.PermissionInfo;
import com.xiongus.bear.console.entity.Permissions;
import com.xiongus.bear.console.repository.PermissionsRepository;
import com.xiongus.bear.console.repository.RowMapperManager;
import com.xiongus.bear.console.service.PermissionsService;
import com.xiongus.bear.domain.Page;
import com.xiongus.bear.domain.PaginationHelper;
import com.xiongus.bear.domain.PaginationHelperImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/** PermissionsServiceImpl. */
@Service
public class PermissionsServiceImpl implements PermissionsService {

  @Resource private JdbcTemplate jdbcTemplate;
  @Resource private PermissionsRepository permissionsRepository;

  @Override
  public Optional<List<Permissions>> getPermissionByUserId(Long userId) {
    return permissionsRepository.findPermissionByUserId(userId);
  }

  @Override
  public Optional<List<Permissions>> getPermissionsByRoleId(Long roleId) {
    return permissionsRepository.getPermissionsByRoleId(roleId);
  }

  @Override
  public Page<PermissionInfo> getPermissionsByRole(String role, int pageNo, int pageSize) {
    PaginationHelper<PermissionInfo> helper =
        new PaginationHelperImpl<PermissionInfo>(jdbcTemplate);

    String sqlCountRows =
        "SELECT count(*) FROM t_sys_permission p,t_sys_role_permission rp,t_sys_role r "
            + "WHERE p.id = rp.permission_id and rp.role_id = r.id";

    String sqlFetchRows =
        "SELECT r.role,p.resource,p.action "
            + "FROM t_sys_permission p,t_sys_role_permission rp,t_sys_role r "
            + "WHERE p.id = rp.permission_id and rp.role_id = r.id";

    String where = " and r.role= ? ";
    List<String> params = new ArrayList<>();
    if (StringUtils.isNotBlank(role)) {
      params = Collections.singletonList(role);
    } else {
      where = " and 1=1 ";
    }

    Page<PermissionInfo> pageInfo =
        helper.fetchPage(
            sqlCountRows + where,
            sqlFetchRows + where,
            params.toArray(),
            pageNo,
            pageSize,
            RowMapperManager.PERMISSION_ROW_MAPPER);

    if (pageInfo == null) {
      pageInfo = new Page<>();
      pageInfo.setTotalCount(0);
      pageInfo.setPageItems(new ArrayList<>());
    }
    return pageInfo;
  }
}
