package com.doganmehmet.app.service;

import com.doganmehmet.app.dto.*;
import com.doganmehmet.app.entity.Bank;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.mapper.IAuditMapper;
import com.doganmehmet.app.mapper.IBankMapper;
import com.doganmehmet.app.mapper.ITransactionMapper;
import com.doganmehmet.app.mapper.IUserMapper;
import com.doganmehmet.app.repository.IAuditRepository;
import com.doganmehmet.app.repository.IBankRepository;
import com.doganmehmet.app.repository.ITransactionRepository;
import com.doganmehmet.app.repository.IUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final IBankMapper m_bankMapper;

    public BankAdminService(IBankRepository bankRepository, IUserRepository userRepository, IAuditRepository auditRepository, ITransactionRepository transactionRepository, IUserMapper userMapper, IAuditMapper auditMapper, ITransactionMapper transactionMapper, IBankMapper bankMapper)
    {
        m_bankRepository = bankRepository;
        m_userRepository = userRepository;
        m_auditRepository = auditRepository;
        m_transactionRepository = transactionRepository;
        m_userMapper = userMapper;
        m_auditMapper = auditMapper;
        m_transactionMapper = transactionMapper;
        m_bankMapper = bankMapper;
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

    public Page<BankDTO> findAllPageable(Pageable pageable)
    {
        return m_bankMapper.toBankDTOPage(m_bankRepository.findAll(pageable));
    }

    public Page<BankUsersDTO> findAllUserByBankNamePageable(String bankName, Pageable pageable)
    {
        var bank = m_bankRepository.findByBankName(bankName)
                .orElseThrow(() -> new ApiException(MyError.BANK_NOT_FOUND));

        return m_userMapper.toBankUsersDTOPage(m_userRepository.findAllByBank(bank, pageable));
    }

    public Page<BankUsersDTO> findAllUsersPageable(Pageable pageable)
    {
        return m_userMapper.toBankUsersDTOPage(m_userRepository.findAll(pageable));
    }

    public Page<UserBalanceDTO> findAllUsersBalancesByBankNamePageable(String bankName, Pageable pageable)
    {
        m_bankRepository.findByBankName(bankName).orElseThrow(() -> new ApiException(MyError.BANK_NOT_FOUND));

        return m_bankRepository.findAllUsersWithBalancesByBankName(bankName, pageable);
    }

    public Page<UserBalanceAllDTO> findAllUsersBalancesPageable(Pageable pageable)
    {
        return m_bankRepository.findAllUsersWithBalances(pageable);
    }

    public Page<TransactionDTO> findTransactionByUsernamePageable(String username, Pageable pageable)
    {
       var user = m_userRepository.findByUsername(username)
               .orElseThrow(() -> new ApiException(MyError.USER_NOT_FOUND));

       return m_transactionMapper.toTransactionDTOPage(m_transactionRepository.findAllByFromUser(user, pageable));
    }

    public Page<TransactionDTO> findAllTransactionPageable(Pageable pageable)
    {
        return m_transactionMapper.toTransactionDTOPage(m_transactionRepository.findAll(pageable));
    }

    public Page<AuditDTO> findAllAuditPageable(Pageable pageable)
    {
        return m_auditMapper.toAuditDTOPage(m_auditRepository.findAll(pageable));
    }

    public Page<AuditDTO> findAuditByUsernamePageable(String username, Pageable pageable)
    {
        m_userRepository.findByUsername(username).orElseThrow(() -> new ApiException(MyError.USER_NOT_FOUND));

        return m_auditMapper.toAuditDTOPage(m_auditRepository.findAllByUsername(username, pageable));
    }
}
