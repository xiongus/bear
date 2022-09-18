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
 * 审计表 Entity.
 *
 * @author xiongus
 */
@Getter
@Setter
@Entity
@Table(name = "sys_audit")
@SQLDelete(sql = "UPDATE sys_audit SET deleted = 1, deleted_time = ROUND(UNIX_TIMESTAMP(NOW(4))*1000) WHERE id = ?")
@Where(clause = "deleted = 0")
public class Audit extends BaseEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 2506062184806035112L;

	/**
	 *	实体ID
	 */
	@Column(name = "entity_id", columnDefinition = "实体ID")
	private Long entityId;

	/**
	 *	实体名称
	 */
	@Column(name = "entity_name", length = 64, columnDefinition = "实体名称")
	private String entityName;

	/**
	 *	操作类型
	 */
	@Column(name = "op_name", length = 128, columnDefinition = "操作类型")
	private String opName;

	/**
	 *	操作内容
	 */
	@Column(name = "comment", length = 255, columnDefinition = "操作内容")
	private String comment;
}
