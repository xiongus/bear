package com.xiongus.bear.console.repository;

import com.xiongus.bear.console.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Users Repository
 *
 * @author xiongus
 */
public interface UsersRepository extends JpaRepository<Users, Long> {

  Optional<Users> getByUsername(String username);
}
