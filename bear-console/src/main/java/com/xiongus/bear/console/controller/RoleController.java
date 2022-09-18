package com.xiongus.bear.console.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Role related methods entry.
 *
 * @author xiongus
 */
@RestController
@AllArgsConstructor
public class RoleController {

  @GetMapping("/v1/roles")
  public Object getRoles(
      @RequestParam(required = false) String keyword,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size) {
    return null;
  }

  @PostMapping("/v1/role")
  public Object createRole(@RequestBody Object obj) {
    return null;
  }

  @PutMapping("/v1/role")
  public Object modifyRole(@RequestBody Object obj) {
    return null;
  }

  @DeleteMapping("/v1/role/{role}")
  public Object deleteRole(@PathVariable String role) {
    return null;
  }

  @DeleteMapping("/v1/roles")
  public Object deleteRoles(@RequestParam List<String> roles) {
    return null;
  }
}
