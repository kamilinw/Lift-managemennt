package com.kamilwnek.lift_management.controller;

import com.kamilwnek.lift_management.dto.CreateBuildingRequest;
import com.kamilwnek.lift_management.entity.Building;
import com.kamilwnek.lift_management.service.BuildingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController()
@RequestMapping("v1/api/building")
public class BuildingController {

    private final BuildingService buildingService;

    @PostMapping(path = "/add")
    public Building createBuilding(@RequestBody @Valid CreateBuildingRequest buildingRequest){
        return buildingService.createBuilding(buildingRequest);
    }
}
