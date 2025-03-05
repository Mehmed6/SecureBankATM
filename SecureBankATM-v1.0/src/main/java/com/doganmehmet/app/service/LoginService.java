package com.doganmehmet.app.service;

import com.doganmehmet.app.dto.LoginRequestDTO;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.security.CustomAuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final CustomAuthenticationProvider m_authenticationProvider;
    private final AuditService m_auditService;

    public LoginService(CustomAuthenticationProvider authenticationProvider, AuditService auditService)
    {
        m_authenticationProvider = authenticationProvider;
        m_auditService = auditService;
    }

    public Authentication login(LoginRequestDTO loginRequestDTO) throws ApiException
    {
        return m_authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()));

    }

}
