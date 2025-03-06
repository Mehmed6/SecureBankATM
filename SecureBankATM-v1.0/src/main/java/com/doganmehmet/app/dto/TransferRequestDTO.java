package com.doganmehmet.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferRequestDTO {

    @NotBlank(message = "Firstname cannot be empty!")
    private String firstName;
    @NotBlank(message = "Lastname cannot be empty!")
    private String lastName;
    @NotBlank(message = "Iban cannot be empty!")
    private String iban;
    @NotNull(message = "Amount cannot be empty!")
    @Positive(message = "Amount must be grater then ZERO!")
    private BigDecimal amount;
    private String description;
}
