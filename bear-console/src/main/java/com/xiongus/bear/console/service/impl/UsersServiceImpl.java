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
import com.xiongus.bear.core.distributed.id.ResourceConstants;
import com.xiongus.bear.domain.Page;
import com.xiongus.bear.domain.PaginationHelper;
import com.xiongus.bear.domain.PaginationHelperImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
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

  @Resource private JdbcTemplate jdbcTemplate;

  @Resource private UsersRepository usersRepository;
  @Resource private RolesRepository rolesRepository;
  @Resource private PermissionsRepository permissionsRepository;

  @Resource private IdGeneratorManager idGeneratorManager;

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
    List<Permissions> permissions = permissionsRepository.findPermissionByUserId(users.getId());
    if (CollectionUtils.isNotEmpty(permissions)) {
      user.setPermissions(
          permissions.stream()
              .map(permission -> String.valueOf(permission.getId()))
              .collect(Collectors.toList()));
    }
    return user;
  }

  @Override
  public Page<User> getUsers(int pageNo, int pageSize) {
    PaginationHelper<User> helper = new PaginationHelperImpl<>(jdbcTemplate);

    String sqlCountRows = "SELECT count(*) FROM users WHERE ";

    String sqlFetchRows = "SELECT username,password,locked,enabled FROM users WHERE ";

    String where = " 1=1 ";
    try {
      return helper.fetchPage(
          sqlCountRows + where,
          sqlFetchRows + where,
          new ArrayList<String>().toArray(),
          pageNo,
          pageSize,
          RowMapperManager.USER_ROW_MAPPER);
    } catch (CannotGetJdbcConnectionException e) {
      LOGGER.error("[db-error] " + e, e);
      throw e;
    }
  }

  @Override
  public List<String> findUserLikeUsername(String username) {
    return usersRepository.findUserLikeUsername(username);
  }

  @Override
  public void createUser(String username, String password) {
    Users users = new Users();
    users.setId(idGeneratorManager.nextId(ResourceConstants.RESOURCE_USER_ID));
    users.setUsername(username);
    users.setPassword(password);
    usersRepository.save(users);
  }

  @Override
  public void deleteUser(String username) {
    usersRepository.deleteByUsername(username);
  }

  @Override
  public void updateUserPassword(String username, String password) {
    usersRepository.updateUserPassword(username, password);
  }
}
