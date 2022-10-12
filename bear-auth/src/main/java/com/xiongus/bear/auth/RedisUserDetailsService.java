package com.xiongus.bear.auth;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private static final String DEF_USERS_BY_USERNAME_QUERY =
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

    public static final String DEF_LOCK_USER_SQL = "update sys_user set locked = ? where username = ?";

    public static final String DEF_CHANGE_PASSWORD_SQL = "update sys_user set password = ? where username = ?";

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

    public void lockUser(String username) {
         jdbcTemplate.update(DEF_LOCK_USER_SQL,username,Boolean.TRUE);
    }

    public void unlockUser(String username) {
        jdbcTemplate.update(DEF_LOCK_USER_SQL,username,Boolean.FALSE);
    }


    public void changePassword(String oldPassword, String newPassword) throws AuthenticationException {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentUser == null) {
            // This would indicate bad coding somewhere
            throw new AccessDeniedException(
                    "Can't change password as no Authentication object found in context " + "for current user.");
        }
        String username = currentUser.getName();
        jdbcTemplate.update(DEF_CHANGE_PASSWORD_SQL, newPassword, username);
        Authentication authentication = createNewAuthentication(currentUser, newPassword);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    private Authentication createNewAuthentication(Authentication currentAuth, String newPassword) {
        UserDetails user = loadUserByUsername(currentAuth.getName());
        UsernamePasswordAuthenticationToken newAuthentication = UsernamePasswordAuthenticationToken.authenticated(user,
                null, user.getAuthorities());
        newAuthentication.setDetails(currentAuth.getDetails());
        return newAuthentication;
    }
}
