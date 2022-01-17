package com.kamilwnek.lift_management.controller;

import com.google.common.net.HttpHeaders;
import com.kamilwnek.lift_management.dto.*;
import com.kamilwnek.lift_management.entity.Lift;
import com.kamilwnek.lift_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request, @RequestHeader(HttpHeaders.USER_AGENT) String device){
        return userService.loginUser(request, device);
    }

    @PostMapping("/register")
    public CreateUserResponse register(@RequestBody @Valid CreateUserRequest request){
        return userService.create(request);
    }

    @GetMapping(value = "/{id}")
    public UserDto getUserById(@PathVariable(name = "id") Long id){
        return userService.getUserById(id);
    }
}
