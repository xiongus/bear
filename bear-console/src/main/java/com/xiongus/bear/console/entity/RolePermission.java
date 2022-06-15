package com.xiongus.bear.console.entity;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * t_sys_role_permission.
 *
 * @author xiongus
 */
@Data
@Entity
@Table(name = "t_sys_role_permission")
public class RolePermission implements Serializable {

  @Serial private static final long serialVersionUID = 8054129568123826336L;

  /** 标识角色的唯一ID */
  @Id
  @Column(name = "role_id", nullable = true, length = 20)
  private Long roleId;

  /** 标识权限的唯一ID */
  @Column(name = "permission_id", nullable = true, length = 20)
  private Long permissionId;
}
