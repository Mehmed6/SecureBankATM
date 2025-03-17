package com.doganmehmet.app.service;

import com.doganmehmet.app.dto.DepositRequestDTO;
import com.doganmehmet.app.dto.TransferRequestDTO;
import com.doganmehmet.app.dto.WithdrawRequestDTO;
import com.doganmehmet.app.enums.Action;
import com.doganmehmet.app.enums.AuditType;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.repository.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class BankingOperationService {
    private final IUserRepository m_userRepository;
    private final AuditService m_auditService;
    private final TransactionService m_transactionService;

    public BankingOperationService(IUserRepository userRepository, AuditService auditService, TransactionService transactionService)
    {
        m_userRepository = userRepository;
        m_auditService = auditService;
        m_transactionService = transactionService;
    }

    public void transfer(String fromUsername, TransferRequestDTO transferRequestDTO) throws ApiException
    {
        var fromUser = m_userRepository.findByUsername(fromUsername)
                .orElseThrow(() -> new ApiException(MyError.USER_NOT_FOUND));

        var toUser = m_userRepository.findByIban(transferRequestDTO.getIban())
                .orElseThrow(() -> new ApiException(MyError.IBAN_NOT_FOUND));

        var amount = transferRequestDTO.getAmount();

        if (fromUser.getBalance().compareTo(amount) < 0) {
            m_auditService.logAudit(fromUsername, AuditType.TRANSFER_FAILED, fromUsername + " has insufficient balance");
            throw new ApiException(MyError.INSUFFICIENT_BALANCE);
        }

        fromUser.setBalance(fromUser.getBalance().subtract(amount));
        toUser.setBalance(toUser.getBalance().add(amount));
        m_userRepository.save(fromUser);
        m_userRepository.save(toUser);

        var fromUserFullName = fromUser.getFirstname() + " " + fromUser.getLastname();
        var toUserFullName = toUser.getFirstname() + " " + toUser.getLastname();

        var description = fromUserFullName + " money transfer to " + toUserFullName;
        m_transactionService.logTransaction(Action.TRANSFER, amount, description, AuditType.TRANSFER_SUCCESS.name(), fromUser, toUser);
        m_auditService.logAudit(fromUsername, AuditType.TRANSFER_SUCCESS, description);
    }

    public void deposit(String username, DepositRequestDTO depositRequestDTO)
    {
        var user = m_userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(MyError.USER_NOT_FOUND));

        user.setBalance(user.getBalance().add(depositRequestDTO.getAmount()));
        m_userRepository.save(user);
        var description = username + " has successfully deposited";
        m_auditService.logAudit(username, AuditType.DEPOSIT_SUCCESS, description);
        m_transactionService.logTransaction(Action.DEPOSIT, depositRequestDTO.getAmount(), description, AuditType.DEPOSIT_SUCCESS.name(), user);
    }

    public void withdraw(String username, WithdrawRequestDTO withdrawRequestDTO)
    {
        var user = m_userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(MyError.USER_NOT_FOUND));

        if (user.getBalance().compareTo(withdrawRequestDTO.getAmount()) < 0) {
            m_auditService.logAudit(username, AuditType.WITHDRAWAL_FAILED, username + " has insufficient balance");
            throw new ApiException(MyError.INSUFFICIENT_BALANCE);
        }

        user.setBalance(user.getBalance().subtract(withdrawRequestDTO.getAmount()));
        m_userRepository.save(user);
        var description = username + " has successfully withdrawn";
        m_auditService.logAudit(username, AuditType.WITHDRAWAL_SUCCESS, description);
        m_transactionService.logTransaction(Action.WITHDRAW, withdrawRequestDTO.getAmount(), description, AuditType.WITHDRAWAL_SUCCESS.name(), user);
    }
}
