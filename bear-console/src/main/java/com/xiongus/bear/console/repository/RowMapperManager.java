package com.xiongus.bear.console.repository;

import com.xiongus.bear.auth.PermissionInfo;
import com.xiongus.bear.auth.RoleInfo;
import com.xiongus.bear.auth.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/** RowMapperManager. */
public class RowMapperManager {

  public static final RowMapper<User> USER_ROW_MAPPER = new UserRowMapper();

  public static final RowMapper<RoleInfo> ROLE_INFO_ROW_MAPPER = new RoleInfoRowMapper();

  public static final PermissionRowMapper PERMISSION_ROW_MAPPER = new PermissionRowMapper();

  /** UserRowMapper. */
  public static final class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      User user = new User();
      user.setUsername(rs.getString("username"));
      user.setPassword(rs.getString("password"));
      user.setLocked(rs.getString("locked"));
      user.setEnabled(rs.getString("enabled"));
      return user;
    }
  }

  /** RoleInfoRowMapper. */
  public static final class RoleInfoRowMapper implements RowMapper<RoleInfo> {

    @Override
    public RoleInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
      RoleInfo roleInfo = new RoleInfo();
      roleInfo.setRole(rs.getString("role"));
      roleInfo.setUsername(rs.getString("username"));
      return roleInfo;
    }
  }

  /** PermissionRowMapper. */
  public static final class PermissionRowMapper implements RowMapper<PermissionInfo> {

    @Override
    public PermissionInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
      PermissionInfo info = new PermissionInfo();
      info.setResource(rs.getString("resource"));
      info.setAction(rs.getString("action"));
      info.setRole(rs.getString("role"));
      return info;
    }
  }
}
