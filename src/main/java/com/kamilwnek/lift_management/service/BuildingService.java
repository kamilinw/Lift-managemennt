package com.kamilwnek.lift_management.service;

import com.kamilwnek.lift_management.dto.BuildingDto;
import com.kamilwnek.lift_management.entity.Building;
import com.kamilwnek.lift_management.mapper.BuildingMapper;
import com.kamilwnek.lift_management.repository.BuildingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final BuildingMapper buildingMapper;

    public Building createBuilding(BuildingDto buildingDto){
        return buildingRepository.save(buildingMapper.toEntity(buildingDto));
    }
}
