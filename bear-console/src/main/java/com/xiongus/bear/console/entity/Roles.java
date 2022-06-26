package com.xiongus.bear.console.entity;

import com.xiongus.bear.common.entity.BaseEntity;
import java.io.Serial;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * 角色表 Entity.
 *
 * @author xiongus
 */
@Getter
@Setter
@Entity
@Table(name = "roles")
@SQLDelete(
    sql =
        "UPDATE roles SET deleted = 1, deleted_time = ROUND(UNIX_TIMESTAMP(NOW(4))*1000) WHERE id = ?")
@Where(clause = "deleted = 0")
public class Roles extends BaseEntity implements Serializable {
  @Serial private static final long serialVersionUID = 7009470794519285690L;

  /** 角色名称 default : '' */
  @Column(name = "name", length = 256, columnDefinition = "角色名称")
  private String name;

  /** 角色标识 default : '' */
  @Column(name = "role", length = 256, columnDefinition = "角色标识")
  private String role;
}
