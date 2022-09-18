package com.xiongus.bear.common.entity;

import java.time.LocalDateTime;
import javax.persistence.*;
import org.apache.commons.lang3.StringUtils;

/**
 * BaseEntity.
 *
 * @author xiongus
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "deleted", columnDefinition = "Bit default '0'")
  protected boolean isDeleted = false;

  @Column(name = "deleted_time", columnDefinition = "Bigint default '0'")
  protected long deletedTime;

  @Column(name = "create_by", nullable = false)
  private String createBy;

  @Column(name = "create_time", nullable = false)
  private LocalDateTime createTime;

  @Column(name = "modify_by", nullable = false)
  private String modifyBy;

  @Column(name = "modify_time", nullable = false)
  private LocalDateTime modifyTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(boolean deleted) {
    isDeleted = deleted;
    // also set deletedAt value as epoch millisecond
    this.deletedTime = System.currentTimeMillis();
  }

  public long getDeletedTime() {
    return deletedTime;
  }

  public void setDeletedTime(long deletedTime) {
    this.deletedTime = deletedTime;
  }

  public String getCreateBy() {
    return createBy;
  }

  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  public LocalDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
  }

  public String getModifyBy() {
    return modifyBy;
  }

  public void setModifyBy(String modifyBy) {
    this.modifyBy = modifyBy;
  }

  public LocalDateTime getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(LocalDateTime modifyTime) {
    this.modifyTime = modifyTime;
  }

  @PrePersist
  protected void prePersist() {
    if (this.createTime == null) {
      createTime = LocalDateTime.now();
    }
    if (this.modifyTime == null) {
      modifyTime = LocalDateTime.now();
    }
    if (this.createBy == null) {
      createBy = StringUtils.EMPTY;
    }
    if (this.modifyBy == null) {
      modifyBy = StringUtils.EMPTY;
    }
  }

  @PreUpdate
  protected void preUpdate() {
    this.modifyTime = LocalDateTime.now();
  }

  @PreRemove
  protected void preRemove() {
    this.modifyTime = LocalDateTime.now();
  }
}
