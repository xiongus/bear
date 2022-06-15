package com.xiongus.bear.console.entity;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * 用户表 Entity
 *
 * @author gus
 */
@Data
@Entity
@Table(name = "t_sys_user")
public class Users implements Serializable {

  @Serial private static final long serialVersionUID = 1914110187085550726L;

  /** 标识用户的唯一ID */
  @Id
  @Column(name = "id", nullable = true, length = 20)
  private Long id;

  /** 用户账号 default : '' */
  @Column(name = "username", nullable = true, length = 64)
  private String username;

  /** 用户密码 default : '' */
  @Column(name = "password", nullable = true, length = 64)
  private String password;

  /** 用户密码 default : '' */
  @Column(name = "mobile", nullable = true, length = 32)
  private String mobile;

  /** 邮箱地址 default : '' */
  @Column(name = "email", nullable = true, length = 64)
  private String email;

  /** 是否锁定，1 是 0 否 default : b'0' */
  @Column(name = "locked", nullable = true, length = 1)
  private String locked;

  /** 是否启用，1 是 0 否 default : b'1' */
  @Column(name = "enabled", nullable = true, length = 1)
  private String enabled;
}
