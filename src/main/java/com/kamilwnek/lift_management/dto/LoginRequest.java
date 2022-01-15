package com.kamilwnek.lift_management.dto;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {

    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;
}
