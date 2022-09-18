package com.xiongus.bear.console.service.impl;

import com.xiongus.bear.console.entity.po.Role;
import com.xiongus.bear.console.repository.RoleRepository;
import com.xiongus.bear.console.service.RoleService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Role service
 *
 * @author xiongus
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

  @Resource private RoleRepository roleRepository;

  @Override
  public Role getByRole(String role) {
    return roleRepository.getRoleByRole(role);
  }
}
