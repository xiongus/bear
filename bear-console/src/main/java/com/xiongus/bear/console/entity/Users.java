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
 * 用户表 Entity.
 *
 * @author xiongus
 */
@Getter
@Setter
@Entity
@Table(name = "users")
@SQLDelete(
    sql =
        "UPDATE users SET deleted = 1, deleted_time = ROUND(UNIX_TIMESTAMP(NOW(4))*1000) WHERE id = ?")
@Where(clause = "deleted = 0")
public class Users extends BaseEntity implements Serializable {
  @Serial private static final long serialVersionUID = 7631360216627069730L;

  /** 用户账号 default : '' */
  @Column(name = "username", length = 64, columnDefinition = "用户账号")
  private String username;

  /** 用户密码 default : '' */
  @Column(name = "password", length = 64, columnDefinition = "用户密码")
  private String password;

  /** 是否锁定，1 是 0 否 default : b'0' */
  @Column(name = "locked", length = 1, columnDefinition = "是否锁定，1 是 0 否")
  private boolean locked;
}
