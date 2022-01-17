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

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String username;
    private String email;
    private ApplicationUserRole applicationUserRole;
}
