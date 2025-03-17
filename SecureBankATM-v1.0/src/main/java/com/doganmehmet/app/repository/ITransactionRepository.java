package com.doganmehmet.app.repository;

import com.doganmehmet.app.entity.Transaction;
import com.doganmehmet.app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findAllByFromUser(User fromUser, Pageable pageable);
}
