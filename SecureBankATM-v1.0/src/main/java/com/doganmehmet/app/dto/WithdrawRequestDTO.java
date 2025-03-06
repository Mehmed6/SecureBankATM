package com.doganmehmet.app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawRequestDTO {
    @NotNull(message = "Amount cannot be empty!")
    @Positive(message = "Amount must be greater then ZERO!")
    private BigDecimal amount;
}
