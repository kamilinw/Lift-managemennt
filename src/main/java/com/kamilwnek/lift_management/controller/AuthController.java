package com.kamilwnek.lift_management.controller;

import com.google.common.net.HttpHeaders;
import com.kamilwnek.lift_management.dto.*;
import com.kamilwnek.lift_management.service.RefreshTokenService;
import com.kamilwnek.lift_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/api/auth")
public class AuthController {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request, @RequestHeader(HttpHeaders.USER_AGENT) String device){
        return userService.loginUser(request, device);
    }

    @PostMapping("/register")
    public CreateUserResponse register(@RequestBody @Valid CreateUserRequest request){
        return userService.create(request);
    }

    @PostMapping("/refresh-token")
    public RefreshTokenResponse refreshToken(@RequestBody @Valid RefreshTokenRequest request, @RequestHeader(HttpHeaders.USER_AGENT) String device){
        return refreshTokenService.refreshToken(request, device);
    }
}
