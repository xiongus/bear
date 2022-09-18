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
 * 角色表 Entity.
 *
 * @author xiongus
 */
@Getter
@Setter
@Entity
@Table(name = "sys_role")
@SQLDelete(sql = "UPDATE sys_role SET deleted = 1, deleted_time = ROUND(UNIX_TIMESTAMP(NOW(4))*1000) WHERE id = ?")
@Where(clause = "deleted = 0")
public class Role extends BaseEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 5352355457058652646L;

	/**
	 *	角色名称
	 */
	@Column(name = "name", length = 64, columnDefinition = "角色名称")
	private String name;

	/**
	 *	角色标识
	 */
	@Column(name = "role", length = 64, columnDefinition = "角色标识")
	private String role;

	/**
	 *	角色描述
	 */
	@Column(name = "comment", length = 255, columnDefinition = "角色描述")
	private String comment;
}
