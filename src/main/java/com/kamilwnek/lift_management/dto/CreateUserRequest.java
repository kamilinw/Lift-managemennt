package com.kamilwnek.lift_management.dto;

import com.kamilwnek.lift_management.validator.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@AllArgsConstructor
@Data
public class CreateUserRequest {

    @NotBlank
    @Size(min = 3, max = 30)
    private String username;

    @NotBlank
    @Email
    @UniqueEmail
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String repeatPassword;
}
