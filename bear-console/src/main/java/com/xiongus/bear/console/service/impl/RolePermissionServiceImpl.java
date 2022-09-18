package com.xiongus.bear.console.service.impl;

import com.xiongus.bear.console.repository.RolePermissionRepository;
import com.xiongus.bear.console.service.RolePermissionService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * RolePermission service
 *
 * @author xiongus
 */
@Service("rolePermissionService")
public class RolePermissionServiceImpl implements RolePermissionService {

	@Resource
	private RolePermissionRepository rolePermissionRepository;


}
