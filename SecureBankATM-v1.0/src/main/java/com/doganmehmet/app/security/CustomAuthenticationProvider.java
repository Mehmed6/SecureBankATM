package com.doganmehmet.app.security;


import com.doganmehmet.app.enums.AuditType;
import com.doganmehmet.app.enums.Status;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.repository.ISessionRepository;
import com.doganmehmet.app.repository.IUserRepository;
import com.doganmehmet.app.service.AuditService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final IUserRepository m_userRepository;
    private final BCryptPasswordEncoder m_passwordEncoder;
    private final AuditService m_auditService;
    private final ISessionRepository m_sessionRepository;

    public CustomAuthenticationProvider(IUserRepository userRepository, BCryptPasswordEncoder passwordEncoder, AuditService auditService, ISessionRepository sessionRepository)
    {
        m_userRepository = userRepository;
        m_passwordEncoder = passwordEncoder;
        m_auditService = auditService;
        m_sessionRepository = sessionRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        var user = m_userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ApiException(MyError.USER_NOT_FOUND));

        if (user.getLoginAttempt() == 3 && !user.getStatus().equals(Status.BLOCKED)) {
            m_auditService.logAudit(user.getUsername(), AuditType.BANNED, "Due to multiple failed login attempts, the account has been locked");
            user.setStatus(Status.BLOCKED);
            m_userRepository.save(user);
        }

        if (user.getLoginAttempt() >= 3)
            throw new ApiException(MyError.USER_BLOCKED);


        if (!m_passwordEncoder.matches( authentication.getCredentials().toString(), user.getPassword())) {
            user.setLoginAttempt(user.getLoginAttempt() + 1);
            m_userRepository.save(user);
            m_auditService.logAudit(user.getUsername(), AuditType.INCORRECT_PASSWORD, "The password has been entered incorrectly: Login attempt: " + user.getLoginAttempt());

            throw new ApiException(MyError.PASSWORD_INCORRECT, "Remaining attempts: " + (3 - user.getLoginAttempt()));
        }

        user.setLoginAttempt(0);
        m_userRepository.save(user);
        m_auditService.logAudit(user.getUsername(), AuditType.LOGIN, "Login successful");

        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
