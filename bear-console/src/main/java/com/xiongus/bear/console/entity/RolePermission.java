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
 * 角色权限表 Entity.
 *
 * @author xiongus
 */
@Getter
@Setter
@Entity
@Table(name = "role_permission")
@SQLDelete(
    sql =
        "UPDATE role_permission SET deleted = 1, deleted_time = ROUND(UNIX_TIMESTAMP(NOW(4))*1000) WHERE id = ?")
@Where(clause = "deleted = 0")
public class RolePermission extends BaseEntity implements Serializable {
  @Serial private static final long serialVersionUID = 4172826344698420285L;

  /** 标识角色的唯一ID */
  @Column(name = "role_id", length = 20, columnDefinition = "标识角色的唯一ID")
  private Long roleId;

  /** 标识权限的唯一ID */
  @Column(name = "permission_id", length = 20, columnDefinition = "标识权限的唯一ID")
  private Long permissionId;
}
