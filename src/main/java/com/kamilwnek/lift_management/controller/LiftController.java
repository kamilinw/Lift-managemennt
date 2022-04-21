package com.kamilwnek.lift_management.controller;

import com.kamilwnek.lift_management.dto.LiftDto;
import com.kamilwnek.lift_management.entity.Lift;
import com.kamilwnek.lift_management.service.LiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/lift")
public class LiftController {
    private final LiftService liftService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Lift createLift(@RequestBody @Valid LiftDto liftRequest){
            return liftService.createLift(liftRequest);
    }

    @GetMapping(value = "/{id}")
    public Lift getLiftById(@PathVariable(name = "id") String id){
        return liftService.getLiftById(id);
    }
}
