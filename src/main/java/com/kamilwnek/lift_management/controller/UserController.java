package com.kamilwnek.lift_management.controller;

import com.kamilwnek.lift_management.dto.*;
import com.kamilwnek.lift_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/{id}")
    public UserDto getUserById(@PathVariable(name = "id") Long id){
        return userService.getUserById(id);
    }
}
