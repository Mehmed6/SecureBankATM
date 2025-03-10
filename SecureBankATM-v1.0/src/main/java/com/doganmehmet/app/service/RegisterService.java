package com.doganmehmet.app.service;

import com.doganmehmet.app.dto.UserDTO;
import com.doganmehmet.app.entity.Bank;
import com.doganmehmet.app.enums.AuditType;
import com.doganmehmet.app.enums.Status;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.mapper.IUserMapper;
import com.doganmehmet.app.repository.IBankRepository;
import com.doganmehmet.app.repository.IUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegisterService {
    private final IUserRepository m_userRepository;
    private final AuditService m_auditService;
    private final IUserMapper m_userMapper;
    private final BCryptPasswordEncoder m_encoder;
    private final IBankRepository m_bankRepository;

    public RegisterService(IUserRepository userRepository, AuditService auditService, IUserMapper userMapper, BCryptPasswordEncoder encoder, IBankRepository bankRepository)
    {
        m_userRepository = userRepository;
        m_auditService = auditService;
        m_userMapper = userMapper;
        m_encoder = encoder;
        m_bankRepository = bankRepository;
    }

    public void saveUser(UserDTO userDTO, Bank bank)
    {
        var date = LocalDateTime.now();
        var user = m_userMapper.toUser(userDTO);
        user.setUpdatedAt(date);
        user.setStatus(Status.ACTIVE);
        user.setPassword(m_encoder.encode(userDTO.getPassword()));
        user.setBank(bank);

        var savedUser = m_userRepository.save(user);
        m_auditService.logAudit(savedUser.getUsername(), AuditType.CREATED, "User created");

    }

    public void register(UserDTO userDTO, Long bankId)
    {
        if (userDTO.getUsername().equalsIgnoreCase("admin"))
            throw new ApiException(MyError.ADMIN_ERROR);

        if (m_userRepository.existsByUsername(userDTO.getUsername()))
            throw new ApiException(MyError.USER_ALREADY_EXISTS);

        if (m_userRepository.existsByEmail(userDTO.getEmail()))
            throw new ApiException(MyError.EMAIL_ALREADY_EXISTS);

        var bank = m_bankRepository.findByBankId(bankId)
                .orElseThrow(() -> new ApiException(MyError.BANK_NOT_FOUND));

        saveUser(userDTO, bank);
    }
}
