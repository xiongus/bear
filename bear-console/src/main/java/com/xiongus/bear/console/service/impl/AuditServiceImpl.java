package com.xiongus.bear.console.service.impl;

import com.xiongus.bear.console.repository.AuditRepository;
import com.xiongus.bear.console.service.AuditService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Audit service
 *
 * @author xiongus
 */
@Service("auditService")
public class AuditServiceImpl implements AuditService {

	@Resource
	private AuditRepository auditRepository;


}
