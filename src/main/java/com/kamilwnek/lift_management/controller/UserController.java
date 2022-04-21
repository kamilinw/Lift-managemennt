package com.kamilwnek.lift_management.controller;

import com.kamilwnek.lift_management.dto.*;
import com.kamilwnek.lift_management.security.JwtTokenUtil;
import com.kamilwnek.lift_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public UserDto getUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String header){
        String id = jwtTokenUtil.getUserIdFromToken(jwtTokenUtil.getTokenFromHttpAuthorizationHeader(header));
        return userService.getUserById(id);
    }
}
