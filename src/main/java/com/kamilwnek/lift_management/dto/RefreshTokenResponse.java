package com.kamilwnek.lift_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshTokenResponse {

    String accessToken;
    String refreshToken;
    String tokenType;
}
