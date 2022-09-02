package com.xiongus.bear.console.enums;

public enum DataStatus {

  ALL("all", ""),
  ACTIVE("active", "0"),
  BANNED("banned", "1");

  final String key;

  final String val;

  DataStatus(String key, String val) {
    this.key = key;
    this.val = val;
  }

  public static String getValByKey(String key) {
    for (DataStatus value : values()) {
      if (value.getKey().equals(key)) {
        return value.val;
      }
    }
    return null;
  }

  public String getKey() {
    return key;
  }

  public String getVal() {
    return val;
  }
}
