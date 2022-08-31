package com.xiongus.bear.console.service.impl;

import com.xiongus.bear.common.Constants;
import com.xiongus.bear.console.entity.Roles;
import com.xiongus.bear.console.entity.Users;
import com.xiongus.bear.console.pojo.DataStatus;
import com.xiongus.bear.console.repository.PermissionsRepository;
import com.xiongus.bear.console.repository.RolesRepository;
import com.xiongus.bear.console.repository.RowMapperManager;
import com.xiongus.bear.console.repository.UsersRepository;
import com.xiongus.bear.console.request.UserRequest;
import com.xiongus.bear.console.service.UsersService;
import com.xiongus.bear.core.distributed.id.IdGeneratorManager;
import com.xiongus.bear.core.distributed.id.ResourceConstants;
import com.xiongus.bear.core.domain.Page;
import com.xiongus.bear.core.domain.PaginationHelper;
import com.xiongus.bear.core.domain.PaginationHelperImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
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
  public Page<Users> getUsers(UserRequest request) {
    PaginationHelper<Users> helper = new PaginationHelperImpl<>(jdbcTemplate);
    List<String> params = new ArrayList<>();
    String sqlCountRows = "SELECT count(*) FROM users u WHERE ";

    String sqlFetchRows =
        "SELECT username,password,locked,deleted"
            + ",id,avatar,display_name,email,mobile_number,address FROM users u WHERE ";

    String where = " 1=1 ";
    if (StringUtils.isNotBlank(request.getStatus())) {
      String deleted = DataStatus.getValByKey(request.getStatus());
      if (deleted == null) {
        throw new IllegalStateException("Unexpected value: " + request.getStatus());
      }
      where += " and deleted = ? ";
      params.add(deleted);
    }
    if (StringUtils.isNotBlank(request.getDisplayName())) {
      where += " and display_name like ?";
      params.add(helper.generateLikeArgument(request.getDisplayName()));
    }
    if (StringUtils.isNotBlank(request.getRole())) {
      where +=
          " and EXISTS (select * from user_role ur where ur.role_id = ? and ur.user_id = u.id) ";
      params.add(request.getRole());
    }
    try {
      return helper.fetchPage(
          sqlCountRows + where,
          sqlFetchRows + where,
          params.toArray(),
          request.getPageNo(),
          request.getPageSize(),
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
  public void deleteUserById(String ids) {
    String[] array = Strings.splitList(ids);
    if (array.length > 1) {
      List<Long> arrayIds = new ArrayList<>();
      for (String id : array) {
        arrayIds.add(Long.parseLong(id));
      }
      List<Users> users = usersRepository.findAllById(arrayIds);
      if (CollectionUtils.isNotEmpty(users)) {
        for (Users user : users) {
          List<Roles> roles = rolesRepository.getRolesByUsername(user.getUsername());
          for (Roles role : roles) {
            if (role.getRole().equals(Constants.GLOBAL_ADMIN_ROLE)) {
              throw new IllegalArgumentException("cannot delete admin: " + user.getUsername());
            }
          }
          usersRepository.deleteById(user.getId());
        }
      }
    } else {
      Optional<Users> usersOptional = usersRepository.findById(Long.parseLong(ids));
      if (usersOptional.isPresent()) {
        Users user = usersOptional.get();
        List<Roles> roles = rolesRepository.getRolesByUsername(user.getUsername());
        for (Roles role : roles) {
          if (role.getRole().equals(Constants.GLOBAL_ADMIN_ROLE)) {
            throw new IllegalArgumentException("cannot delete admin: " + user.getUsername());
          }
        }
        usersRepository.deleteById(user.getId());
      }
    }
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
  public void updateUserPassword(String username, String password) {
    usersRepository.updateUserPassword(username, password);
  }

  @Override
  public Users findUserByUsername(String username) {
    return usersRepository.findByUsername(username);
  }
}
