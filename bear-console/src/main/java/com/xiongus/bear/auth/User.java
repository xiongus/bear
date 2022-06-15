package com.xiongus.bear.auth;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * User.
 */
public class User implements Serializable {

  @Serial private static final long serialVersionUID = 817569871068868607L;

  private String username;

  private String password;

  private List<String> roles;

  private List<String> permissions;

  /** 是否锁定，1 是 0 否. */
  private String locked;

  /** 是否启用，1 是 0 否. */
  private String enabled;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public List<String> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<String> permissions) {
    this.permissions = permissions;
  }

  public String getLocked() {
    return locked;
  }

  public void setLocked(String locked) {
    this.locked = locked;
  }

  public String getEnabled() {
    return enabled;
  }

  public void setEnabled(String enabled) {
    this.enabled = enabled;
  }

}
