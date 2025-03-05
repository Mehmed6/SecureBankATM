package com.doganmehmet.app.security;

import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.repository.IUserRepository;
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

    public CustomAuthenticationProvider(IUserRepository userRepository, BCryptPasswordEncoder passwordEncoder)
    {
        m_userRepository = userRepository;
        m_passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        var user = m_userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ApiException(MyError.USER_NOT_FOUND));

        if (user.getLoginAttempt() >= 3)
            throw new ApiException(MyError.USER_BLOCKED);


        if (!m_passwordEncoder.matches( authentication.getCredentials().toString(), user.getPassword())) {
            user.setLoginAttempt(user.getLoginAttempt() + 1);
            m_userRepository.save(user);
            throw new ApiException(MyError.PASSWORD_INCORRECT, "Remaining attempts: " + (3 - user.getLoginAttempt()));
        }

        user.setLoginAttempt(0);
        m_userRepository.save(user);

        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
