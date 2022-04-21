package com.kamilwnek.lift_management.dto;

import com.kamilwnek.lift_management.enums.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class UserDto {

    private String id;
    private String username;
    private String email;
    private ApplicationUserRole applicationUserRole;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
