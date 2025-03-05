package com.doganmehmet.app.config;

import com.doganmehmet.app.security.CustomAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String LOGIN = "/auth/login";
    private final String REGISTER = "/register";

    private final CustomAuthenticationProvider m_authenticationProvider;

    public SecurityConfig(CustomAuthenticationProvider authenticationProvider)
    {
        m_authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .authorizeHttpRequests(request ->
                        request.requestMatchers(LOGIN, REGISTER).permitAll()
                                .requestMatchers("/welcome").authenticated()
                                .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession)
                        .maximumSessions(1)
                        .expiredUrl("/auth/login?expired=true")
                        .maxSessionsPreventsLogin(false)
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl(LOGIN).permitAll());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager()
    {
        return new ProviderManager(List.of(m_authenticationProvider));
    }
}
