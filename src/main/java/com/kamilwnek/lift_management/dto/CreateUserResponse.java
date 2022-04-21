package com.kamilwnek.lift_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserResponse {

    private String id;
    private String username;
    private String email;
}
