package com.kamilwnek.lift_management.controller;

import com.kamilwnek.lift_management.dto.CreateUserRequest;
import com.kamilwnek.lift_management.dto.CreateUserResponse;
import com.kamilwnek.lift_management.dto.LoginRequest;
import com.kamilwnek.lift_management.dto.LoginResponse;
import com.kamilwnek.lift_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request){
        return userService.loginUser(request);
    }

    @PostMapping("/register")
    public CreateUserResponse register(@RequestBody @Valid CreateUserRequest request){
        return userService.create(request);
    }
}
