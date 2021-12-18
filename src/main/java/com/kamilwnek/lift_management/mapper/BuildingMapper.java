package com.kamilwnek.lift_management.mapper;

import com.kamilwnek.lift_management.dto.BuildingDto;
import com.kamilwnek.lift_management.entity.Building;
import org.springframework.stereotype.Component;

@Component
public class BuildingMapper implements DtoMapper<Building, BuildingDto> {
    @Override
    public Building toEntity(BuildingDto dto) {
        return new Building(
                dto.getId(),
                dto.getName(),
                dto.getCity(),
                dto.getAddress()
        );
    }

    @Override
    public BuildingDto toDto(Building entity) {
        return new BuildingDto(
                entity.getId(),
                entity.getName(),
                entity.getCity(),
                entity.getAddress()
        );
    }
}
