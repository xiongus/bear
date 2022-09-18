package com.xiongus.bear.console.repository;

import com.xiongus.bear.console.entity.po.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Audit Repository
 *
 * @author xiongus
 */
public interface AuditRepository extends JpaRepository<Audit, Long> {

}
