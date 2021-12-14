package com.kamilwnek.lift_management.controller;

import com.kamilwnek.lift_management.dto.CreateLiftRequest;
import com.kamilwnek.lift_management.entity.Lift;
import com.kamilwnek.lift_management.service.LiftService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("v1/api/lift")
public class LiftController {
    private final LiftService liftService;

    @PostMapping(value = "/add")
    public Lift createLift(@RequestBody @Valid CreateLiftRequest liftRequest){
        try{
            return liftService.createLift(liftRequest);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request", e);
        }

    }
}
