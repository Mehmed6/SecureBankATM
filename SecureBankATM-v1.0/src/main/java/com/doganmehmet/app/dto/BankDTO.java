package com.doganmehmet.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankDTO {

    private long bankId;
    private String bankName;
    private String swiftCode;
}
