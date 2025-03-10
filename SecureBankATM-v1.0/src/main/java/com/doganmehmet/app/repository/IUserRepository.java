package com.doganmehmet.app.repository;

import com.doganmehmet.app.entity.Bank;
import com.doganmehmet.app.entity.User;
import com.doganmehmet.app.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByIban(String iban);


    boolean existsByRole(Role role);

    Page<User> findAllByBank(Bank bank, Pageable pageable);
}
