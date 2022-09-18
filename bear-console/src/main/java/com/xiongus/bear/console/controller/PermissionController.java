package com.xiongus.bear.console.controller;

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
 * Permission related methods entry.
 *
 * @author xiongus
 */
@RestController
@AllArgsConstructor
public class PermissionController {

  @GetMapping("/v1/permissions")
  public Object getPermissions(
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) String role,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size) {
    return null;
  }

  @PostMapping("/v1/permission")
  public Object createPermission(@RequestBody Object obj) {
    return null;
  }

  @PutMapping("/v1/permission")
  public Object modifyPermission(@RequestBody Object obj) {
    return null;
  }

  @DeleteMapping("/v1/permission/{resource}/{action}")
  public Object deletePermission(@PathVariable String resource, @PathVariable String action) {
    return null;
  }
}
