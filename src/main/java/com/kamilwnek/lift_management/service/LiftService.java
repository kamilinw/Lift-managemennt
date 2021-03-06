package com.kamilwnek.lift_management.service;

import com.kamilwnek.lift_management.dto.BuildingDto;
import com.kamilwnek.lift_management.dto.LiftDto;
import com.kamilwnek.lift_management.entity.Building;
import com.kamilwnek.lift_management.entity.Lift;
import com.kamilwnek.lift_management.exception.NoSuchRecordException;
import com.kamilwnek.lift_management.exception.ValidationException;
import com.kamilwnek.lift_management.mapper.BuildingMapper;
import com.kamilwnek.lift_management.mapper.LiftMapper;
import com.kamilwnek.lift_management.repository.BuildingRepository;
import com.kamilwnek.lift_management.repository.LiftRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class LiftService {

    private final LiftRepository liftRepository;
    private final BuildingRepository buildingRepository;
    private final LiftMapper liftMapper;
    private final BuildingMapper buildingMapper;

    public Lift createLift(LiftDto liftDto) {
        BuildingDto buildingDto = liftDto.getBuildingDto();

        if(checkIfBuildingIdIsNull(buildingDto)){
            Building createdBuilding = createNewBuilding(buildingDto);
            liftDto.setBuildingDto(buildingMapper.toDto(createdBuilding));
        } else{
            Building building = buildingRepository
                    .findById(UUID.fromString(buildingDto.getId()))
                    .orElseThrow(
                            ()-> new NoSuchRecordException(String.format("Building with id %s not found", buildingDto.getId()))
                    );
            liftDto.setBuildingDto(buildingMapper.toDto(building));
        }
        return liftRepository.save(liftMapper.toEntity(liftDto));
    }

    private boolean checkIfBuildingIdIsNull(BuildingDto buildingDto) {
        if (buildingDto.getId() == null){
            validateBody(buildingDto);
            return true;
        }
        return false;
    }

    private void validateBody(BuildingDto buildingDto){
        if (buildingDto.getAddress() == null)
            throw new ValidationException("address can not be null!");
        else if (buildingDto.getCity() == null)
            throw new ValidationException("city can not be null!");
        else if (buildingDto.getName() == null)
            throw new ValidationException("name can not be null!");
    }

    private Building createNewBuilding(BuildingDto buildingDto) {
        Building building = buildingMapper.toEntity(buildingDto);
        return buildingRepository.save(building);
    }

    public Lift getLiftById(String idString) {
        UUID id = UUID.fromString(idString);
        return liftRepository.findById(id).orElseThrow(
                () -> new NoSuchRecordException(String.format("Lift with id %s not found", idString))
        );
    }
}
