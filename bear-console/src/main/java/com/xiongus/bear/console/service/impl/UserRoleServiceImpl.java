package com.xiongus.bear.console.service.impl;

import com.xiongus.bear.console.repository.UserRoleRepository;
import com.xiongus.bear.console.service.UserRoleService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * UserRole service
 *
 * @author xiongus
 */
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

	@Resource
	private UserRoleRepository userRoleRepository;

}
