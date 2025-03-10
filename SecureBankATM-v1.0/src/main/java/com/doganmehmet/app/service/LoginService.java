package com.doganmehmet.app.service;

import com.doganmehmet.app.dto.LoginRequestDTO;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.security.CustomAuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final CustomAuthenticationProvider m_authenticationProvider;

    public LoginService(CustomAuthenticationProvider authenticationProvider)
    {
        m_authenticationProvider = authenticationProvider;
    }

    public Authentication login(LoginRequestDTO loginRequestDTO) throws ApiException
    {
        return m_authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()));

    }

}
