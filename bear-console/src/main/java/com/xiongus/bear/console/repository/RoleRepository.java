package com.xiongus.bear.console.repository;

import com.xiongus.bear.console.entity.po.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Role Repository
 *
 * @author xiongus
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

  Role getRoleByRole(String role);
}
