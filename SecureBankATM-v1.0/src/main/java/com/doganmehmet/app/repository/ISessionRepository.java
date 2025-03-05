package com.doganmehmet.app.repository;

import com.doganmehmet.app.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISessionRepository extends JpaRepository<Session, Long> {
}
