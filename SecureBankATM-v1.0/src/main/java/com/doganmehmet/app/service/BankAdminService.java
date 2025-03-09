package com.doganmehmet.app.service;

import com.doganmehmet.app.dto.*;
import com.doganmehmet.app.entity.Bank;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.mapper.IAuditMapper;
import com.doganmehmet.app.mapper.ITransactionMapper;
import com.doganmehmet.app.mapper.IUserMapper;
import com.doganmehmet.app.repository.IAuditRepository;
import com.doganmehmet.app.repository.IBankRepository;
import com.doganmehmet.app.repository.ITransactionRepository;
import com.doganmehmet.app.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAdminService {
    private final IBankRepository m_bankRepository;
    private final IUserRepository m_userRepository;
    private final IAuditRepository m_auditRepository;
    private final ITransactionRepository m_transactionRepository;
    private final IUserMapper m_userMapper;
    private final IAuditMapper m_auditMapper;
    private final ITransactionMapper m_transactionMapper;

    public BankAdminService(IBankRepository bankRepository, IUserRepository userRepository, IAuditRepository auditRepository, ITransactionRepository transactionRepository, IUserMapper userMapper, IAuditMapper auditMapper, ITransactionMapper transactionMapper)
    {
        m_bankRepository = bankRepository;
        m_userRepository = userRepository;
        m_auditRepository = auditRepository;
        m_transactionRepository = transactionRepository;
        m_userMapper = userMapper;
        m_auditMapper = auditMapper;
        m_transactionMapper = transactionMapper;
    }

    public void saveBank(String bankName)
    {
        if (m_bankRepository.existsByBankName(bankName))
            throw new ApiException(MyError.BANK_ALREADY_EXISTS);

        var bank = new Bank();
        bank.setBankName(bankName);
        m_bankRepository.save(bank);
    }

    public List<Bank> findAll()
    {
        return m_bankRepository.findAll();
    }

    public List<BankUsersDTO> findAllUserByBankName(String bankName)
    {
        return m_userMapper.toBankUsersDTOList(m_bankRepository.findByBankName(bankName)
                .orElseThrow(() -> new ApiException(MyError.BANK_NOT_FOUND)).getUsers());
    }

    public List<BankUsersDTO> findAllUsers()
    {
        return m_userMapper.toBankUsersDTOList(m_userRepository.findAll());
    }

    public List<UserBalanceDTO> findAllUsersBalancesByBankName(String bankName)
    {
        m_bankRepository.findByBankName(bankName).orElseThrow(() -> new ApiException(MyError.BANK_NOT_FOUND));

        return m_bankRepository.findAllUsersWithBalancesByBankName(bankName);
    }

    public List<UserBalanceAllDTO> findAllUsersBalances()
    {
        return m_bankRepository.findAllUsersWithBalances();
    }

    public List<TransactionDTO> findTransactionByUsername(String username)
    {
        return m_transactionMapper.toListTransactionDTO(m_userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(MyError.USER_NOT_FOUND)).getTransactions());
    }

    public List<TransactionDTO> findAllTransaction()
    {
        return m_transactionMapper.toListTransactionDTO(m_transactionRepository.findAll());
    }

    public List<AuditDTO> findAuditByUsername(String username)
    {
        m_userRepository.findByUsername(username).orElseThrow(() -> new ApiException(MyError.USER_NOT_FOUND));

        return m_auditMapper.toAuditDTOList(m_auditRepository.findByUsername(username));
    }

    public List<AuditDTO> findAllAudit()
    {
        return m_auditMapper.toAuditDTOList(m_auditRepository.findAll());
    }
}
