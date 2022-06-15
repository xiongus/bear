package com.xiongus.bear.console.service.impl;

import com.xiongus.bear.auth.User;
import com.xiongus.bear.console.entity.Permissions;
import com.xiongus.bear.console.entity.Roles;
import com.xiongus.bear.console.entity.Users;
import com.xiongus.bear.console.repository.PermissionsRepository;
import com.xiongus.bear.console.repository.RolesRepository;
import com.xiongus.bear.console.repository.RowMapperManager;
import com.xiongus.bear.console.repository.UsersRepository;
import com.xiongus.bear.console.service.UsersService;
import com.xiongus.bear.core.distributed.id.IdGeneratorManager;
import com.xiongus.bear.domain.Page;
import com.xiongus.bear.domain.PaginationHelper;
import com.xiongus.bear.domain.PaginationHelperImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/** UsersServiceImpl. */
@Service
public class UsersServiceImpl implements UsersService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UsersServiceImpl.class);

  private static final String RESOURCE_USER_ID = "user_id";

  private static final String RESOURCE_ROLE_ID = "role_id";

  private static final String RESOURCE_PERMISSION_ID = "permission_id";

  @Resource private JdbcTemplate jdbcTemplate;

  @Resource private UsersRepository usersRepository;
  @Resource private RolesRepository rolesRepository;
  @Resource private PermissionsRepository permissionsRepository;

  @Resource private IdGeneratorManager idGeneratorManager;

  /** init IdGeneratorManager. */
  @PostConstruct
  public void init() {
    idGeneratorManager.register(RESOURCE_USER_ID, RESOURCE_ROLE_ID, RESOURCE_PERMISSION_ID);
  }

  @Override
  public User findUserByUsername(String username) {
    Optional<Users> optionalUsers = usersRepository.getByUsername(username);
    if (optionalUsers.isEmpty()) {
      return null;
    }
    User user = new User();
    Users users = optionalUsers.get();
    BeanUtils.copyProperties(users, user);
    Optional<List<Roles>> optionalRoles = rolesRepository.findRoleByUserId(users.getId());
    if (optionalRoles.isPresent()) {
      List<Roles> roles = optionalRoles.get();
      user.setRoles(roles.stream().map(Roles::getRole).collect(Collectors.toList()));
    }
    Optional<List<Permissions>> optionalPermissions =
        permissionsRepository.findPermissionByUserId(users.getId());
    if (optionalPermissions.isPresent()) {
      List<Permissions> permissions = optionalPermissions.get();
      user.setPermissions(
          permissions.stream().map(Permissions::getPermission).collect(Collectors.toList()));
    }
    return user;
  }

  @Override
  public Page<User> getUsers(int pageNo, int pageSize) {
    PaginationHelper<User> helper = new PaginationHelperImpl<>(jdbcTemplate);

    String sqlCountRows = "SELECT count(*) FROM t_sys_user WHERE ";

    String sqlFetchRows = "SELECT username,password,locked,enabled FROM t_sys_user WHERE ";

    String where = " 1=1 ";
    try {
      Page<User> pageInfo =
          helper.fetchPage(
              sqlCountRows + where,
              sqlFetchRows + where,
              new ArrayList<String>().toArray(),
              pageNo,
              pageSize,
              RowMapperManager.USER_ROW_MAPPER);
      if (pageInfo == null) {
        pageInfo = new Page<>();
        pageInfo.setTotalCount(0);
        pageInfo.setPageItems(new ArrayList<>());
      }
      return pageInfo;
    } catch (CannotGetJdbcConnectionException e) {
      LOGGER.error("[db-error] " + e.toString(), e);
      throw e;
    }
  }

  @Override
  public void createUser(String username, String password) {
    Users users = new Users();
    users.setUsername(username);
    users.setPassword(password);
    usersRepository.save(users);
  }
}
