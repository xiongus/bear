package com.xiongus.bear.console.repository;

import com.xiongus.bear.console.entity.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Users Repository
 *
 * @author xiongus
 */
public interface UsersRepository extends JpaRepository<Users, Long> {

  Optional<Users> getByUsername(String username);

  @Query(value = "SELECT u.username FROM Users u where u.username like %:username%")
  List<String> findUserLikeUsername(@Param("username") String username);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM Users u  WHERE u.username = :username")
  void deleteByUsername(@Param("username") String username);

  @Modifying
  @Transactional
  @Query(value = "UPDATE Users u SET u.password = :password WHERE u.username = :username")
  void updateUserPassword(@Param("username") String username, @Param("password") String password);
}
