package com.xiongus.bear.console.entity;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * t_sys_role.
 *
 * @author xiongus
 */
@Data
@Entity
@Table(name = "t_sys_role")
public class Roles implements Serializable {

  @Serial private static final long serialVersionUID = 2669283083422943674L;

  /** 标识角色的唯一ID */
  @Id
  @Column(name = "id", nullable = true, length = 20)
  private Long id;

  /** 角色标识 default : '' */
  @Column(name = "role", nullable = true, length = 256)
  private String role;

  /** 角色名称 default : '' */
  @Column(name = "name", nullable = true, length = 256)
  private String name;

  /** 1: deleted, 0: normal default : b'0' */
  @Column(name = "deleted", nullable = true, length = 1)
  private String deleted;
}
