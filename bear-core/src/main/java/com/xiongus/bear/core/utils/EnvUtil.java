package com.xiongus.bear.core.utils;

import org.springframework.core.env.ConfigurableEnvironment;

/**
 * EnvUtil.
 */
public class EnvUtil {

  private static ConfigurableEnvironment environment;

  public static ConfigurableEnvironment getEnvironment() {
    return environment;
  }

  public static void setEnvironment(ConfigurableEnvironment environment) {
    EnvUtil.environment = environment;
  }

  public static boolean containsProperty(String key) {
    return environment.containsProperty(key);
  }

  public static String getProperty(String key) {
    return environment.getProperty(key);
  }

  public static String getProperty(String key, String defaultValue) {
    return environment.getProperty(key, defaultValue);
  }

  public static <T> T getProperty(String key, Class<T> targetType) {
    return environment.getProperty(key, targetType);
  }

  public static <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
    return environment.getProperty(key, targetType, defaultValue);
  }
}
