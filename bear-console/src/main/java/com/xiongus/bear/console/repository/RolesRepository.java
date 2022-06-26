package com.xiongus.bear.console.repository;

import com.xiongus.bear.console.entity.Roles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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
  List<Roles> getRolesByUsername(String username);

  @Query(
      value =
          "SELECT concat(r.role,'-',r.name)  FROM Roles r "
              + "where r.role like %:role% or r.name like %:role%")
  List<String> findRolesLikeRoleName(@Param("role") String role);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM Roles r  WHERE r.role = :role")
  void deleteByRole(@Param("role") String role);

  @Modifying
  @Transactional
  @Query(value = "UPDATE Roles r SET r.name = :name WHERE r.role = :role")
  void updateRoleName(@Param("role") String role, @Param("name") String name);
}
