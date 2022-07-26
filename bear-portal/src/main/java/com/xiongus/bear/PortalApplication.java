package com.xiongus.bear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * PortalApplication.
 *
 * @author xiongus
 */
@EnableDiscoveryClient
@SpringBootApplication
public class PortalApplication {

  public static void main(String[] args) {
    SpringApplication.run(PortalApplication.class, args);
  }
}
