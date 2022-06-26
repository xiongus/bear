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
 * 权限表 Entity.
 *
 * @author xiongus
 */
@Getter
@Setter
@Entity
@Table(name = "permissions")
@SQLDelete(
    sql =
        "UPDATE permissions SET deleted = 1, deleted_time = ROUND(UNIX_TIMESTAMP(NOW(4))*1000) WHERE id = ?")
@Where(clause = "deleted = 0")
public class Permissions extends BaseEntity implements Serializable {
  @Serial private static final long serialVersionUID = 1115543166567369410L;

  /** 权限名称 default : '' */
  @Column(name = "name", length = 64, columnDefinition = "权限名称")
  private String name;

  /** 资源 default : '' */
  @Column(name = "resource", length = 64, columnDefinition = "资源")
  private String resource;

  /** 操作 default : '' */
  @Column(name = "action", length = 64, columnDefinition = "操作")
  private String action;

  /** 资源类型 default : '' */
  @Column(name = "resource_type", length = 64, columnDefinition = "资源类型")
  private String resourceType;

  /** 权限父ID default : 0 */
  @Column(name = "parent_id", length = 20, columnDefinition = "权限父ID")
  private Long parentId;
}
