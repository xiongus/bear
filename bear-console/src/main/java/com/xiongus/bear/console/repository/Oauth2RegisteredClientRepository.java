package com.xiongus.bear.console.repository;

import com.xiongus.bear.console.entity.po.Oauth2RegisteredClient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Oauth2RegisteredClient Repository
 *
 * @author xiongus
 */
public interface Oauth2RegisteredClientRepository extends JpaRepository<Oauth2RegisteredClient, String> {

}
