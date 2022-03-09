package com.kamilwnek.lift_management.mapper;

import com.kamilwnek.lift_management.dto.LiftDto;
import com.kamilwnek.lift_management.entity.Lift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LiftMapper implements DtoMapper<Lift, LiftDto> {

    @Autowired
    private final BuildingMapper buildingMapper;

    public LiftMapper(BuildingMapper buildingMapper) {
        this.buildingMapper = buildingMapper;
    }

    @Override
    public Lift toEntity(LiftDto dto) {
        return new Lift(
                //dto.getId(),
                buildingMapper.toEntity(dto.getBuildingDto()),
                dto.getSerialNumber(),
                dto.getUdtNumber(),
                dto.getActivationDate(),
                dto.getComment()
        );
    }

    @Override
    public LiftDto toDto(Lift entity) {
        return new LiftDto(
                entity.getId(),
                buildingMapper.toDto(entity.getBuilding()),
                entity.getSerialNumber(),
                entity.getUdtNumber(),
                entity.getActivationDate(),
                entity.getComment()
        );
    }
}
