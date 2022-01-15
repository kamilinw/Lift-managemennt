package com.kamilwnek.lift_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    String accessToken;
    String refreshToken;
}
