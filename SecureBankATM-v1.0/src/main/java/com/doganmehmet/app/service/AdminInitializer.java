package com.doganmehmet.app.service;

import com.doganmehmet.app.entity.User;
import com.doganmehmet.app.enums.Role;
import com.doganmehmet.app.enums.Status;
import com.doganmehmet.app.repository.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final IUserRepository m_userRepository;
    private final BCryptPasswordEncoder m_bCryptPasswordEncoder;

    public AdminInitializer(IUserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        m_userRepository = userRepository;
        m_bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String... args)
    {
        if (!m_userRepository.existsByRole(Role.ADMIN)) {
            var admin = new User();
            admin.setUsername("admin");
            admin.setPassword(m_bCryptPasswordEncoder.encode("admin"));
            admin.setEmail("admin@gmail.com");
            admin.setFirstname("Admin");
            admin.setLastname("Admin");
            admin.setRole(Role.ADMIN);
            admin.setStatus(Status.ACTIVE);
            admin.setUpdatedAt(LocalDateTime.now());
            m_userRepository.save(admin);

            System.out.printf("Admin has been created. Username:%s password:%s", admin.getUsername(), admin.getPassword());

        }
    }
}
