package com.doganmehmet.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDTO {

    private String transactionType;

    private BigDecimal amount;

    private String description;

    private LocalDateTime transactionDate;

    private String transactionStatus;

    private String fromUser;

    private String toUser;
}
