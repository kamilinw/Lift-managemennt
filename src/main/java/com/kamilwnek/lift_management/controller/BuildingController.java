package com.kamilwnek.lift_management.controller;

import com.kamilwnek.lift_management.dto.BuildingDto;
import com.kamilwnek.lift_management.entity.Building;
import com.kamilwnek.lift_management.service.BuildingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController()
@RequestMapping("api/v1/building")
public class BuildingController {

    private final BuildingService buildingService;

    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Building createBuilding(@RequestBody @Valid BuildingDto buildingRequest){
        return buildingService.createBuilding(buildingRequest);
    }
}
