package com.xiongus.bear.console.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

  private String role;
  private String status;
  private String displayName;
  private int pageNo;
  private int pageSize;

  public UserRequest(String role, String status, String displayName, int pageNo, int pageSize) {
    this.role = role;
    this.status = status;
    this.displayName = displayName;
    this.pageNo = pageNo;
    this.pageSize = pageSize;
  }
}
