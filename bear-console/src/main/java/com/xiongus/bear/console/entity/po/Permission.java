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
 * 权限表 Entity.
 *
 * @author xiongus
 */
@Getter
@Setter
@Entity
@Table(name = "sys_permission")
@SQLDelete(sql = "UPDATE sys_permission SET deleted = 1, deleted_time = ROUND(UNIX_TIMESTAMP(NOW(4))*1000) WHERE id = ?")
@Where(clause = "deleted = 0")
public class Permission extends BaseEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 3063409323930172323L;

	/**
	 *	权限名称
	 */
	@Column(name = "name", length = 64, columnDefinition = "权限名称")
	private String name;

	/**
	 *	资源
	 */
	@Column(name = "resource", length = 64, columnDefinition = "资源")
	private String resource;

	/**
	 *	资源类型,app-系统、menu-菜单、action-操作
	 */
	@Column(name = "resource_type", length = 64, columnDefinition = "资源类型,app-系统、menu-菜单、action-操作")
	private String resourceType;

	/**
	 *	操作方式,GET、POST、PUT、DELETE
	 */
	@Column(name = "action", length = 64, columnDefinition = "操作方式,GET、POST、PUT、DELETE")
	private String action;

	/**
	 *	主键父ID
	 * default : 0
	 */
	@Column(name = "parent_id", columnDefinition = "主键父ID")
	private Long parentId;

	/**
	 *	权限描述
	 */
	@Column(name = "comment", length = 255, columnDefinition = "权限描述")
	private String comment;
}
