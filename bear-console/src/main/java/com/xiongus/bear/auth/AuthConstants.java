package com.xiongus.bear.auth;

/** AuthConstants. */
public class AuthConstants {

  public static final String AUTHORIZATION_HEADER = "Authorization";

  public static final String ACCESS_TOKEN = "accessToken";

  public static final String TOKEN_TTL = "tokenTtl";

  public static final String GLOBAL_ADMIN = "globalAdmin";

  public static final String TOKEN_PREFIX = "Bearer ";

  public static final String USERNAME = "username";

  public static final String PASSWORD = "password";

  public static final String CONSOLE_RESOURCE_NAME_PREFIX = "console/";

  public static final String UPDATE_PASSWORD_ENTRY_POINT =
      CONSOLE_RESOURCE_NAME_PREFIX + "user/password";
}
