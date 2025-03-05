package com.doganmehmet.app.service;

import com.doganmehmet.app.dto.UserDTO;
import com.doganmehmet.app.enums.AuditType;
import com.doganmehmet.app.enums.Status;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.mapper.IUserMapper;
import com.doganmehmet.app.repository.ITransactionRepository;
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

    public RegisterService(IUserRepository userRepository, AuditService auditService, IUserMapper userMapper, BCryptPasswordEncoder encoder)
    {
        m_userRepository = userRepository;
        m_auditService = auditService;
        m_userMapper = userMapper;
        m_encoder = encoder;
    }

    public void saveUser(UserDTO userDTO)
    {
        var date = LocalDateTime.now();
        var user = m_userMapper.toUser(userDTO);
        user.setUpdatedAt(date);
        user.setStatus(Status.ACTIVE);
        user.setPassword(m_encoder.encode(userDTO.getPassword()));

        var savedUser = m_userRepository.save(user);
        m_auditService.logAudit(savedUser.getUsername(), AuditType.CREATED, "User created");

    }

    public void register(UserDTO userDTO)
    {
        if (m_userRepository.existsByUsername(userDTO.getUsername()))
            throw new ApiException(MyError.USER_ALREADY_EXISTS);

        if (m_userRepository.existsByEmail(userDTO.getEmail()))
            throw new ApiException(MyError.EMAIL_ALREADY_EXISTS);

        saveUser(userDTO);
    }
}
