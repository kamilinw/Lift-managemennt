package com.kamilwnek.lift_management.controller;

import com.kamilwnek.lift_management.dto.LiftDto;
import com.kamilwnek.lift_management.entity.Lift;
import com.kamilwnek.lift_management.service.LiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/api/lift")
public class LiftController {
    private final LiftService liftService;

    @PostMapping(value = "/add")
    public LiftDto createLift(@RequestBody @Valid LiftDto liftRequest){
        try{
            return liftService.createLift(liftRequest);
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request", e);
        } catch (NoSuchElementException elementException){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No building with this id", elementException);
        }
    }

    @GetMapping(value = "/{id}")
    public Lift getLiftById(@PathVariable(name = "id") Long id){
        return liftService.getLiftById(id);
    }
}
