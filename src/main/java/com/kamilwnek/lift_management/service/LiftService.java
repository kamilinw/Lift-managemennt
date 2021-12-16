package com.kamilwnek.lift_management.service;

import com.kamilwnek.lift_management.dto.CreateLiftRequest;
import com.kamilwnek.lift_management.entity.Building;
import com.kamilwnek.lift_management.entity.Lift;
import com.kamilwnek.lift_management.repository.BuildingRepository;
import com.kamilwnek.lift_management.repository.LiftRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LiftService {

    private final LiftRepository liftRepository;
    private final BuildingRepository buildingRepository;


    public Lift createLift(CreateLiftRequest liftRequest) {

        Lift lift = new Lift(
                buildingRepository.findById(liftRequest.getBuildingId()).get(),
                liftRequest.getSerialNumber(),
                liftRequest.getUdtNumber(),
                liftRequest.getActivationDate(),
                liftRequest.getComment()
        );
        return liftRepository.save(lift);
    }

    public Lift getLiftById(Long id) {
        return liftRepository.findById(id).get();
    }
}
