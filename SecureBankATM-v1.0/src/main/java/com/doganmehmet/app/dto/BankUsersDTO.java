package com.doganmehmet.app.dto;

import com.doganmehmet.app.entity.Bank;
import com.doganmehmet.app.enums.Role;
import com.doganmehmet.app.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class BankUsersDTO {

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String iban;
    private Role role = Role.USER;
    private Status status;
    private BigDecimal balance;
    private int loginAttempt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Bank bank;
}
