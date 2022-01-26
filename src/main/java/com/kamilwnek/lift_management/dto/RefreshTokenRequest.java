package com.kamilwnek.lift_management.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class RefreshTokenRequest {

    @NotNull
    private String refreshToken;
}
