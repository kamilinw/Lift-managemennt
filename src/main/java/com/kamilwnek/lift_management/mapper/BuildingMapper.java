package com.kamilwnek.lift_management.mapper;

import com.kamilwnek.lift_management.dto.BuildingDto;
import com.kamilwnek.lift_management.entity.Building;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BuildingMapper implements DtoMapper<Building, BuildingDto> {
    @Override
    public Building toEntity(BuildingDto dto) {
        UUID id = null;
        if (dto.getId() != null)
            id = UUID.fromString(dto.getId());

        return new Building(
                id,
                dto.getName(),
                dto.getCity(),
                dto.getAddress()
        );
    }

    @Override
    public BuildingDto toDto(Building entity) {
        return new BuildingDto(
                entity.getId().toString(),
                entity.getName(),
                entity.getCity(),
                entity.getAddress()
        );
    }
}
