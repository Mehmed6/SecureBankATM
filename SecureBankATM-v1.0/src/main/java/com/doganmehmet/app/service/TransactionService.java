package com.doganmehmet.app.service;

import com.doganmehmet.app.entity.Transaction;
import com.doganmehmet.app.entity.User;
import com.doganmehmet.app.enums.Action;
import com.doganmehmet.app.repository.ITransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {
    private final ITransactionRepository m_transactionRepository;

    public TransactionService(ITransactionRepository transactionRepository)
    {
        m_transactionRepository = transactionRepository;
    }

    public void logTransaction(Action action, BigDecimal amount, String description, String status, User fromUser, User toUser)
    {
        m_transactionRepository.save(new Transaction(action, amount, description, LocalDateTime.now(), status, fromUser, toUser));
    }

    public void logTransaction(Action action, BigDecimal amount, String description, String status, User fromUser)
    {
        m_transactionRepository.save(new Transaction(action, amount, description, LocalDateTime.now(), status, fromUser, null));
    }
}
