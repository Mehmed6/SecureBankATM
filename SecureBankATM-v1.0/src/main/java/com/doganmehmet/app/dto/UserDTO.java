package com.doganmehmet.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    @NotBlank(message = "Username cannot be empty!")
    private String username;
    @NotBlank(message = "Password cannot be empty!")
    private String password;
    @NotBlank(message = "Firstname cannot be empty!")
    private String firstname;
    @NotBlank(message = "Lastname cannot be empty!")
    private String lastname;
    @NotBlank(message = "Email cannot be empty!")
    private String email;

}
