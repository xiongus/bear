package com.xiongus.bear.console.repository;

import com.xiongus.bear.console.entity.Roles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Roles Repository
 *
 * @author xiongus
 */
public interface RolesRepository extends JpaRepository<Roles, Long> {

  @Query(
      value =
          "SELECT r FROM Roles r,UserRole ur " + "WHERE r.id = ur.roleId " + "and ur.userId = ?1")
  Optional<List<Roles>> findRoleByUserId(Long userId);

  @Query(
      value =
          "SELECT r FROM Roles r,UserRole ur, Users u "
              + "WHERE r.id = ur.roleId "
              + "and ur.userId = u.id "
              + "and u.username = ?1")
  Optional<List<Roles>> getRolesByUsername(String username);
}
