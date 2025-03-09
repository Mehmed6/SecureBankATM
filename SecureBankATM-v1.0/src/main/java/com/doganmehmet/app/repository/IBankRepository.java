package com.doganmehmet.app.repository;

import com.doganmehmet.app.dto.UserBalanceAllDTO;
import com.doganmehmet.app.dto.UserBalanceDTO;
import com.doganmehmet.app.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface IBankRepository extends JpaRepository<Bank, Long> {


    Optional<Bank> findByBankName(String bankName);

    boolean existsByBankName(String bankName);

    @Query("select new com.doganmehmet.app.dto.UserBalanceDTO(u.username, u.balance) from User u where u.bank.bankName = :bankName")
    List<UserBalanceDTO> findAllUsersWithBalancesByBankName(@Param("bankName") String bankName);

    @Query("SELECT new com.doganmehmet.app.dto.UserBalanceAllDTO(u.username, u.balance, u.bank.bankName) FROM User u")
    List<UserBalanceAllDTO> findAllUsersWithBalances();


    Optional<Bank> findByBankId(Long bankId);
}
