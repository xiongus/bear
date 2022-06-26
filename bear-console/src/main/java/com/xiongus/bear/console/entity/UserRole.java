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
 * 用户角色表 Entity.
 *
 * @author xiongus
 */
@Getter
@Setter
@Entity
@Table(name = "user_role")
@SQLDelete(
    sql =
        "UPDATE user_role SET deleted = 1, deleted_time = ROUND(UNIX_TIMESTAMP(NOW(4))*1000) WHERE id = ?")
@Where(clause = "deleted = 0")
public class UserRole extends BaseEntity implements Serializable {
  @Serial private static final long serialVersionUID = 4075506450518680868L;

  /** 标识用户的唯一ID */
  @Column(name = "user_id", length = 20, columnDefinition = "标识用户的唯一ID")
  private Long userId;

  /** 标识角色的唯一ID */
  @Column(name = "role_id", length = 20, columnDefinition = "标识角色的唯一ID")
  private Long roleId;
}
