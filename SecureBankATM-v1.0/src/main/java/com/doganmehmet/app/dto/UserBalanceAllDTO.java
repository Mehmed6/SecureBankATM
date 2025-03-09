package com.doganmehmet.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBalanceAllDTO {
    private String username;
    private BigDecimal balance;
    private String bankName;
}
