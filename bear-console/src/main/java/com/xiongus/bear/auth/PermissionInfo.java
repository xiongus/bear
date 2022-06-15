package com.xiongus.bear.auth;

import java.io.Serial;
import java.io.Serializable;

/** Permission to auth. */
public class PermissionInfo implements Serializable {

  @Serial private static final long serialVersionUID = -3583076254743606551L;

  /** Role name. */
  private String role;

  /** An unique key of resource. */
  private String resource;

  /** Action on resource, refer to class ActionTypes. */
  private String action;

  public PermissionInfo() {}

  public PermissionInfo(String resource, String action) {
    this.resource = resource;
    this.action = action;
  }

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "PermissionInfo{"
        + "role='"
        + role
        + '\''
        + ", resource='"
        + resource
        + '\''
        + ", action='"
        + action
        + '\''
        + '}';
  }
}
