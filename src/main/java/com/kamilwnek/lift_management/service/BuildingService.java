package com.kamilwnek.lift_management.service;

import com.kamilwnek.lift_management.dto.CreateBuildingRequest;
import com.kamilwnek.lift_management.entity.Building;
import com.kamilwnek.lift_management.repository.BuildingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BuildingService {

    private final BuildingRepository buildingRepository;

    public Building createBuilding(CreateBuildingRequest createBuildingRequest){
        Building building = new Building(
                createBuildingRequest.getName(),
                createBuildingRequest.getCity(),
                createBuildingRequest.getAddress()
        );
        return buildingRepository.save(building);
    }
}
