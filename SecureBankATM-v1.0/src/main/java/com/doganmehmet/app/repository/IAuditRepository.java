package com.doganmehmet.app.repository;

import com.doganmehmet.app.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuditRepository extends JpaRepository<Audit, Long> {
}
