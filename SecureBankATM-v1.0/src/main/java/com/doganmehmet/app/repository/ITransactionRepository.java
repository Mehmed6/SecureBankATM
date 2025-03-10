package com.doganmehmet.app.repository;

import com.doganmehmet.app.entity.Transaction;
import com.doganmehmet.app.entity.User;
import com.doganmehmet.app.enums.Action;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {

//    m_transactionRepository.save(new Transaction(Action.TRANSFER, amount, description,
//                                                 LocalDateTime.now(), AuditType.TRANSFER_SUCCESS.name(),fromUser, toUser));

    default void logTransaction(Action action, BigDecimal amount, String description, String status, User fromUser, User toUser)
    {
        save(new Transaction(action, amount, description, LocalDateTime.now(), status, fromUser, toUser));
    }

    default void logTransaction(Action action, BigDecimal amount, String description, String status, User fromUser)
    {
        save(new Transaction(action, amount, description, LocalDateTime.now(), status, fromUser, null));
    }

    Page<Transaction> findAllByFromUser(User fromUser, Pageable pageable);
}
