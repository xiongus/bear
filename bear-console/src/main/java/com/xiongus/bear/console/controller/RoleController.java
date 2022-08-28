package com.xiongus.bear.console.controller;

import com.xiongus.bear.console.service.RolesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Role related methods entry.
 *
 * @author xiongus
 */
@RestController("/role")
@AllArgsConstructor
@RequestMapping({"/api/role", "/v1/api/role"})
public class RoleController {

  private final RolesService rolesService;

  /**
   * Get paged roles.
   *
   * @param pageNo number index of page
   * @param pageSize size of page
   * @return A collection of roles, empty set if no role is found
   */
  @GetMapping
  public Object getRoles(@RequestParam int pageNo, @RequestParam int pageSize) {
    return rolesService.getRolesByUserName("", pageNo, pageSize);
  }
}
