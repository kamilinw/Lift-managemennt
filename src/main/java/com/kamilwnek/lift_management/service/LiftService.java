package com.kamilwnek.lift_management.service;

import com.kamilwnek.lift_management.dto.CreateLiftRequest;
import com.kamilwnek.lift_management.entity.Lift;
import com.kamilwnek.lift_management.repository.LiftRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LiftService {

    private final LiftRepository liftRepository;


    public Lift createLift(CreateLiftRequest liftRequest) {
        Lift lift = new Lift(
                liftRequest.getBuilding(),
                liftRequest.getSerialNumber(),
                liftRequest.getUdtNumber(),
                liftRequest.getActivationDate(),
                liftRequest.getComment()
        );
        return liftRepository.save(lift);
    }
}
