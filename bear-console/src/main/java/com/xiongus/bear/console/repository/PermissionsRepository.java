package com.xiongus.bear.console.repository;

import com.xiongus.bear.console.entity.Permissions;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Permissions Repository
 *
 * @author xiongus
 */
public interface PermissionsRepository extends JpaRepository<Permissions, Long> {

  @Query(
      value =
          "SELECT p FROM Permissions p,RolePermission rp,UserRole ur "
              + "WHERE p.id = rp.permissionId "
              + "AND rp.roleId = ur.roleId "
              + "AND ur.userId = ?1")
  Optional<List<Permissions>> findPermissionByUserId(Long userId);

  @Query(
      value =
          "SELECT p FROM Permissions p,RolePermission rp "
              + "WHERE p.id = rp.permissionId  "
              + "AND rp.roleId = ?1")
  Optional<List<Permissions>> getPermissionsByRoleId(Long roleId);
}
