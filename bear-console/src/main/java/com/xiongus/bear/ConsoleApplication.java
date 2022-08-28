package com.xiongus.bear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * ConsoleApplication.
 *
 * @author xiongus
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ConsoleApplication {
  public static void main(String[] args) {
    SpringApplication.run(ConsoleApplication.class, args);
  }
}
