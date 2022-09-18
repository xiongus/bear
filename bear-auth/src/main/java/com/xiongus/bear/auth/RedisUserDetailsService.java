package com.xiongus.bear.auth;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * RedisUserDetailsService.
 * @author xiongus
 */
@Slf4j
public record RedisUserDetailsService(
        JdbcTemplate jdbcTemplate, RedisTemplate<String, Object> redisTemplate) implements UserDetailsService {

    private static final String namespace = "user_details:";

    public static final String DEF_USERS_BY_USERNAME_QUERY =
            "select username,password,locked,deleted"
                    + " ,id,avatar_url,display_name,email,phone_number,address "
                    + " from sys_user " + "where username = ?";


    private static final String DEF_USERS_BY_AUTHORITIES_QUERY =  "select r.role from sys_user_role ur \n" +
            "INNER JOIN sys_role r on ur.role_id = r.id\n" +
            "where user_id = ?\n" +
            "UNION ALL\n" +
            "select p.resource from sys_user_role ur \n" +
            "INNER JOIN sys_role_permission rp on ur.role_id = rp.role_id\n" +
            "INNER JOIN sys_permission p on rp.permission_id = p.id\n" +
            "where user_id = ?";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = (UserInfo) redisTemplate.opsForValue().get(namespace.concat(username));
        if (userInfo != null) {
            return userInfo;
        }
        List<UserInfo> users = loadUsersByUsername(username);
        if (users.size() == 0) {
            log.debug("Query returned no results for user '" + username + "'");
            throw new UsernameNotFoundException(String.format("Username %s not found", username));
        }
        redisTemplate.opsForValue().set(namespace.concat(username),users.get(0));
        // contains no GrantedAuthority[]
        return users.get(0);
    }

    /**
     * Executes the SQL <tt>usersByUsernameQuery</tt> and returns a list of UserDetails objects. There
     * should normally only be one matching user.
     */
    private List<UserInfo> loadUsersByUsername(String username) {
        // @formatter:off
        RowMapper<UserInfo> mapper =
                (rs, rowNum) -> {
                    String username1 = rs.getString(1);
                    String password = rs.getString(2);
                    boolean locked = rs.getBoolean(3);
                    boolean deleted = rs.getBoolean(4);
                    long userId = rs.getLong(5);
                    String avatar = rs.getString(6);
                    String displayName = rs.getString(7);
                    String email = rs.getString(8);
                    String phoneNumber = rs.getString(9);
                    String address = rs.getString(10);
                    // get role and permission
                    List<String> authorities = this.loadAuthoritiesByUserId(userId);
                    return new UserInfo(
                            username1, password, !deleted, true, true, !locked,
                            AuthorityUtils.createAuthorityList(authorities.toArray(new String[0])),
                            userId,avatar,displayName,email,phoneNumber,address);
                };
        // @formatter:on
        return jdbcTemplate.query(DEF_USERS_BY_USERNAME_QUERY, mapper, username);
    }

    private List<String> loadAuthoritiesByUserId(long userId){
        return jdbcTemplate.queryForList(DEF_USERS_BY_AUTHORITIES_QUERY, String.class, userId, userId);
    }
}
