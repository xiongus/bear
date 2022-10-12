package com.xiongus.bear.auth.component;

import com.xiongus.bear.auth.Constants;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationFailureLockEventHandler implements AuthenticationFailureHandler {


    private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private final RedisTemplate<String, Object> redisTemplate;

    public static final Long  LOGIN_ERROR_TIMES = 5L;

    public static final Long  DELTA_TIME = 1L;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 只处理账号密码错误异常
        if (!(exception instanceof BadCredentialsException)) {
            return;
        }
        String username = request.getParameter("username");
        String key = Constants.LOGIN_ERROR_TIMES.concat(username);
        Long times = redisTemplate.opsForValue().increment(key);
        // 自动过期时间
        redisTemplate.expire(key, DELTA_TIME, TimeUnit.HOURS);
        if (LOGIN_ERROR_TIMES <= times) {
            throw new LockedException(
                    messages.getMessage(
                            "AbstractUserDetailsAuthenticationProvider.locked",
                            "Login failed " + times + ",User account is locked"));
        }
    }
}
