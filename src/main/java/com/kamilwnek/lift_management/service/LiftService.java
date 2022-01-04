package com.kamilwnek.lift_management.service;

import com.kamilwnek.lift_management.dto.LiftDto;
import com.kamilwnek.lift_management.entity.Building;
import com.kamilwnek.lift_management.entity.Lift;
import com.kamilwnek.lift_management.exception.NoSuchRecordException;
import com.kamilwnek.lift_management.mapper.BuildingMapper;
import com.kamilwnek.lift_management.mapper.LiftMapper;
import com.kamilwnek.lift_management.repository.BuildingRepository;
import com.kamilwnek.lift_management.repository.LiftRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LiftService {

    private final LiftRepository liftRepository;
    private final BuildingRepository buildingRepository;
    private final LiftMapper liftMapper;
    private final BuildingMapper buildingMapper;

    public LiftDto createLift(LiftDto liftRequest) {
        Long buildingId = liftRequest.getBuildingDto().getId();
        if(buildingId == null){
            Building building = buildingMapper.toEntity(liftRequest.getBuildingDto());
            Building savedBuilding = buildingRepository.save(building);
            liftRequest.setBuildingDto(buildingMapper.toDto(savedBuilding));
        } else{
            Building building = buildingRepository
                    .findById(buildingId)
                    .orElseThrow(
                            ()-> new NoSuchRecordException(String.format("Building with id %d not found", buildingId))
                    );
            liftRequest.setBuildingDto(buildingMapper.toDto(building));
        }
        Lift lift = liftRepository.save(liftMapper.toEntity(liftRequest));

        return liftMapper.toDto(lift);
    }

    public Lift getLiftById(Long id) {
        Optional<Lift> liftOptional = liftRepository.findById(id);
        if (liftOptional.isPresent())
            return liftOptional.get();
        else
            throw new NoSuchRecordException(String.format("Lift with id %d not found", id));
    }
}
