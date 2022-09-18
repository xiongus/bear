package com.xiongus.bear.console.service.impl;

import com.xiongus.bear.console.repository.PermissionRepository;
import com.xiongus.bear.console.service.PermissionService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Permission service
 *
 * @author xiongus
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	@Resource
	private PermissionRepository permissionRepository;


}
