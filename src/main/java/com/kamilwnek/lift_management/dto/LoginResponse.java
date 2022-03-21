package com.kamilwnek.lift_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private UserDto user;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
}
