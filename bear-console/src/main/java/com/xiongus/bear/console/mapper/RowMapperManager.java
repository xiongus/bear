package com.xiongus.bear.console.mapper;

import com.xiongus.bear.console.entity.dto.UserDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class RowMapperManager {

    public static final RowMapper<UserDTO> USER_ROW_MAPPER = new UserRowMapper();

    /** UserRowMapper. */
    public static final class UserRowMapper implements RowMapper<UserDTO> {

        @Override
        public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserDTO user = new UserDTO();
            user.setUsername(rs.getString("username"));
            user.setAvatarUrl(rs.getString("avatar_url"));
            user.setDisplayName(rs.getString("display_name"));
            user.setEmail(rs.getString("email"));
            user.setPhoneNumber(rs.getString("phone_number"));
            user.setAddress(rs.getString("address"));
            user.setStatus(rs.getBoolean("enabled"));
            user.setVerify(rs.getBoolean("is_verify"));
            return user;
        }
    }
}
