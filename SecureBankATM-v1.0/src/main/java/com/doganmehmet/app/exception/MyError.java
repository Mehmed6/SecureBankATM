package com.doganmehmet.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@Accessors(prefix = "m_")
public enum MyError {

    GENERAL_ERROR("1000", "General error: %s"),
    USER_NOT_FOUND("1001", "User not found!"),
    USER_ALREADY_EXISTS("1002", "User already exists!"),
    EMAIL_ALREADY_EXISTS("1003", "Email already exists!"),
    PASSWORD_INCORRECT("1004", "Password incorrect! %s"),
    USER_BLOCKED("1005", "Your account has been locked due to 3 incorrect password attempts!"),
    INSUFFICIENT_BALANCE("1006", "Insufficient balance!"),
    BANK_NOT_FOUND("1007", "Bank not found!"),
    BANK_ALREADY_EXISTS("1008", "Bank already exists!"),
    ADMIN_ERROR("1009", "This username is reserved for admins only!"),
    IBAN_NOT_FOUND("1010", "Iban not found!"),
    WRITE_TO_FILE_EXCEPTION("1011", "An error occurred while writing the file!"),

    ;

    private final String m_errorCode;
    private final String m_errorMessage;

}
