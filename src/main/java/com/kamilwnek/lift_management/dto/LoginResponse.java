package com.kamilwnek.lift_management.dto;

import com.kamilwnek.lift_management.enums.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LoginResponse {

    Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String username;
    private String email;
    private ApplicationUserRole applicationUserRole;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
}
