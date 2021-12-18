package com.kamilwnek.lift_management.mapper;

import com.kamilwnek.lift_management.dto.LiftDto;
import com.kamilwnek.lift_management.entity.Lift;
import org.springframework.stereotype.Component;

@Component
public class LiftMapper implements DtoMapper<Lift, LiftDto> {
    @Override
    public Lift toEntity(LiftDto dto) {
        return new Lift(
                dto.getId(),
                dto.getBuilding(),
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
                entity.getBuilding(),
                entity.getSerialNumber(),
                entity.getUdtNumber(),
                entity.getActivationDate(),
                entity.getComment()
        );
    }
}
