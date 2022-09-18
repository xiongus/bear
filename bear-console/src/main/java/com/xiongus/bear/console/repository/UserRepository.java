package com.xiongus.bear.console.repository;

import com.xiongus.bear.console.entity.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * User Repository
 *
 * @author xiongus
 */
public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);

  @Modifying
  @Transactional
  @Query(value = "UPDATE User u SET u.password = :password WHERE u.username = :username")
  void updateUserPassword(@Param("username") String username, @Param("password") String password);

}
