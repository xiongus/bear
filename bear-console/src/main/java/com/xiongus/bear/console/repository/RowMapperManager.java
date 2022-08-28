package com.xiongus.bear.console.repository;

import com.xiongus.bear.console.entity.Permissions;
import com.xiongus.bear.console.entity.Roles;
import com.xiongus.bear.console.entity.Users;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/** RowMapperManager. */
public class RowMapperManager {

  public static final RowMapper<Users> USER_ROW_MAPPER = new UserRowMapper();

  public static final RowMapper<Roles> ROLE_INFO_ROW_MAPPER = new RoleRowMapper();

  public static final RowMapper<Permissions> PERMISSION_ROW_MAPPER = new PermissionRowMapper();

  /** UserRowMapper. */
  public static final class UserRowMapper implements RowMapper<Users> {

    @Override
    public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
      Users user = new Users();
      user.setUsername(rs.getString("username"));
      user.setPassword(rs.getString("password"));
      user.setLocked(rs.getBoolean("locked"));
      user.setDeleted(rs.getBoolean("deleted"));
      user.setId(rs.getLong("id"));
      user.setAvatar(rs.getString("avatar"));
      user.setDisplayName(rs.getString("display_name"));
      user.setEmail(rs.getString("email"));
      user.setMobileNumber(rs.getString("mobile_number"));
      user.setAddress(rs.getString("address"));
      return user;
    }
  }

  /** RoleRowMapper. */
  public static final class RoleRowMapper implements RowMapper<Roles> {

    @Override
    public Roles mapRow(ResultSet rs, int rowNum) throws SQLException {
      Roles role = new Roles();
      role.setId(rs.getLong("id"));
      role.setName(rs.getString("name"));
      role.setRole(rs.getString("role"));
      return role;
    }
  }

  /** PermissionRowMapper. */
  public static final class PermissionRowMapper implements RowMapper<Permissions> {

    @Override
    public Permissions mapRow(ResultSet rs, int rowNum) throws SQLException {
      Permissions info = new Permissions();
      info.setId(rs.getLong("id"));
      info.setParentId(rs.getLong("parent_id"));
      info.setName(rs.getString("name"));
      info.setResource(rs.getString("resource"));
      info.setAction(rs.getString("action"));
      info.setResourceType(rs.getString("resource_type"));
      return info;
    }
  }
}
