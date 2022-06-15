package com.xiongus.bear.console.entity;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * t_sys_permission.
 *
 * @author xiongus
 */
@Data
@Entity
@Table(name = "t_sys_permission")
public class Permissions implements Serializable {

  @Serial private static final long serialVersionUID = 3426099160172654471L;

  /** 标识权限的唯一ID */
  @Id
  @Column(name = "id", nullable = true, length = 20)
  private Long id;

  /** 权限名称 default : '' */
  @Column(name = "name", nullable = true, length = 64)
  private String name;

  /** 权限标识 default : '' */
  @Column(name = "permission", nullable = true, length = 64)
  private String permission;

  /** 资源 default : '' */
  @Column(name = "resource", nullable = true, length = 64)
  private String resource;

  /** 操作 default : '' */
  @Column(name = "action", nullable = true, length = 64)
  private String action;

  /** 资源类型 default : '' */
  @Column(name = "resource_type", nullable = true, length = 64)
  private String resourceType;

  /** 权限父ID default : 0 */
  @Column(name = "parent_id", nullable = true, length = 20)
  private Long parentId;

  /** 1: deleted, 0: normal default : b'0' */
  @Column(name = "deleted", nullable = true, length = 1)
  private String deleted;
}
