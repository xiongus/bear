package com.xiongus.bear.console.entity;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * t_sys_user_role.
 *
 * @author xiongus
 */
@Data
@Entity
@Table(name = "t_sys_user_role")
public class UserRole implements Serializable {

  @Serial private static final long serialVersionUID = 3166995269995586773L;

  /** 标识用户的唯一ID */
  @Id
  @Column(name = "user_id", nullable = true, length = 20)
  private Long userId;

  /** 标识角色的唯一ID */
  @Column(name = "role_id", nullable = true, length = 20)
  private Long roleId;
}
