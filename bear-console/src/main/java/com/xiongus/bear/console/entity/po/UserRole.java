package com.xiongus.bear.console.entity.po;

import com.xiongus.bear.common.entity.BaseEntity;
import java.io.Serial;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * 用户角色关联表 Entity.
 *
 * @author xiongus
 */
@Getter
@Setter
@Entity
@Table(name = "sys_user_role")
@SQLDelete(sql = "UPDATE sys_user_role SET deleted = 1, deleted_time = ROUND(UNIX_TIMESTAMP(NOW(4))*1000) WHERE id = ?")
@Where(clause = "deleted = 0")
public class UserRole extends BaseEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 7402473249021056026L;

	/**
	 *	用户ID
	 */
	@Column(name = "user_id", columnDefinition = "用户ID")
	private Long userId;

	/**
	 *	角色ID
	 */
	@Column(name = "role_id", columnDefinition = "角色ID")
	private Long roleId;
}
