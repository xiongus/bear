package com.xiongus.bear.console.service.impl;

import com.xiongus.bear.console.entity.Users;
import com.xiongus.bear.console.repository.PermissionsRepository;
import com.xiongus.bear.console.repository.RolesRepository;
import com.xiongus.bear.console.repository.RowMapperManager;
import com.xiongus.bear.console.repository.UsersRepository;
import com.xiongus.bear.console.service.UsersService;
import com.xiongus.bear.core.distributed.id.IdGeneratorManager;
import com.xiongus.bear.core.distributed.id.ResourceConstants;
import com.xiongus.bear.core.domain.Page;
import com.xiongus.bear.core.domain.PaginationHelper;
import com.xiongus.bear.core.domain.PaginationHelperImpl;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  public Page<Users> getUsers(int pageNo, int pageSize) {
    PaginationHelper<Users> helper = new PaginationHelperImpl<>(jdbcTemplate);

    String sqlCountRows = "SELECT count(*) FROM users WHERE ";

    String sqlFetchRows = "SELECT username,password,locked,deleted" +
            ",id,avatar,display_name,email,mobile_number,address FROM users WHERE ";

    String where = " 1=1 ";
    try {
      return helper.fetchPage(
          sqlCountRows + where,
          sqlFetchRows + where,
          new ArrayList<String>().toArray(),
          pageNo,
          pageSize,
          RowMapperManager.USER_ROW_MAPPER);
    } catch (Exception e) {
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

  @Override
  public Users findUserByUsername(String username) {
    return usersRepository.findByUsername(username);
  }
}
