package com.xiongus.bear.console.service.impl;

import com.xiongus.bear.console.entity.dto.UserDTO;
import com.xiongus.bear.console.entity.po.Role;
import com.xiongus.bear.console.entity.po.User;
import com.xiongus.bear.console.enums.DataStatus;
import com.xiongus.bear.console.mapper.RowMapperManager;
import com.xiongus.bear.console.mapper.UserMapper;
import com.xiongus.bear.console.repository.RoleRepository;
import com.xiongus.bear.console.repository.UserRepository;
import com.xiongus.bear.console.service.UserService;
import com.xiongus.bear.core.domain.PageDTO;
import com.xiongus.bear.core.domain.PaginationHelper;
import com.xiongus.bear.core.domain.PaginationHelperImpl;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * User service
 *
 * @author xiongus
 */
@Service("userService")
public class UserServiceImpl implements UserService {

  @Resource private UserRepository userRepository;

  @Resource private RoleRepository roleRepository;

  @Resource private JdbcTemplate jdbcTemplate;

  @Override
  public PageDTO<UserDTO> getUsers(String keyword, String role, String status, Pageable pageable) {
    PaginationHelper<UserDTO> helper = new PaginationHelperImpl<>(jdbcTemplate);
    List<String> params = new ArrayList<>();
    String sqlCountRows = "SELECT count(*) FROM sys_user u WHERE ";

    String sqlFetchRows = "SELECT * FROM sys_user u WHERE ";

    String where = " 1=1 ";
    if (StringUtils.isNotBlank(status)) {
      String deleted = DataStatus.getValByKey(status);
      if (deleted == null) {
        throw new IllegalStateException("Unexpected value: " + status);
      }
      where += " and deleted = ? ";
      params.add(deleted);
    }
    if (StringUtils.isNotBlank(keyword)) {
      where += " and display_name like ?";
      params.add(helper.generateLikeArgument(keyword));
    }
    if (StringUtils.isNotBlank(role)) {
      Role role1 = roleRepository.getRoleByRole(role);
      if (role1 != null) {
        where +=
            " and EXISTS (select * from sys_user_role ur where ur.role_id = ? and ur.user_id = u.id) ";
        params.add(String.valueOf(role1.getId()));
      }
    }
    return helper.fetchPage(
        sqlCountRows + where,
        sqlFetchRows + where,
        params.toArray(),
        pageable.getPageNumber(),
        pageable.getPageSize(),
        RowMapperManager.USER_ROW_MAPPER);
  }

  @Override
  public UserDTO getUserByUsername(String username) {
    return UserMapper.INSTANCE.convert(userRepository.findByUsername(username));
  }

  @Override
  public User findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public void updateUserPassword(String username, String password) {
    userRepository.updateUserPassword(username, password);
  }

  @Override
  public void deleteUserById(long userId) {
    userRepository.deleteById(userId);
  }

  @Override
  public void createUser(UserDTO user) {

  }

  @Override
  public void updateUser(UserDTO user) {

  }
}
