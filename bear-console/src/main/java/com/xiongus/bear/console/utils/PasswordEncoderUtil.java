package com.xiongus.bear.console.utils;

import java.util.UUID;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** Password encoder tool. */
public class PasswordEncoderUtil {

  public static Boolean matches(String raw, String encoded) {
    return new BCryptPasswordEncoder().matches(raw, encoded);
  }

  public static String encode(String raw) {
    return new BCryptPasswordEncoder().encode(raw);
  }


}
