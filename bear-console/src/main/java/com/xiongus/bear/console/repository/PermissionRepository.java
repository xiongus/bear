package com.xiongus.bear.console.repository;

import com.xiongus.bear.console.entity.po.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Permission Repository
 *
 * @author xiongus
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
