package com.xiongus.bear.console.entity.po;

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
@Table(name = "sys_user")
@SQLDelete(
    sql =
        "UPDATE sys_user SET deleted = 1, deleted_time = ROUND(UNIX_TIMESTAMP(NOW(4))*1000) WHERE id = ?")
@Where(clause = "deleted = 0")
public class User extends BaseEntity implements Serializable {
  @Serial private static final long serialVersionUID = 2151073557419796081L;

  /** 用户账号 */
  @Column(name = "username", length = 64, columnDefinition = "用户账号")
  private String username;

  /** 用户密码 */
  @Column(name = "password", length = 64, columnDefinition = "用户密码")
  private String password;

  /** 用户头像 */
  @Column(name = "avatar_url", length = 128, columnDefinition = "用户头像")
  private String avatarUrl;

  /** 用户名称 */
  @Column(name = "display_name", length = 64, columnDefinition = "用户名称")
  private String displayName;

  /** 电子邮箱 */
  @Column(name = "email", length = 64, columnDefinition = "电子邮箱")
  private String email;

  /** 联系方式 */
  @Column(name = "phone_number", length = 64, columnDefinition = "联系方式")
  private String phoneNumber;

  /** 联系地址 */
  @Column(name = "address", length = 64, columnDefinition = "联系地址")
  private String address;

  /** 是否有效，1 是 0 否 default : '0'::"bit" */
  @Column(name = "enabled", columnDefinition = "是否有效，1 是 0 否")
  private boolean enabled;

  /** 是否锁定，1 是 0 否 default : '0'::"bit" */
  @Column(name = "locked", columnDefinition = "是否锁定，1 是 0 否")
  private boolean locked;

  /** 是否验证，1 是 0 否 default : '0'::"bit" */
  @Column(name = "is_verify", columnDefinition = "是否验证，1 是 0 否")
  private boolean isVerify;
}
