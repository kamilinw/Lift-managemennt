package com.kamilwnek.lift_management.service;

import com.kamilwnek.lift_management.dto.LiftDto;
import com.kamilwnek.lift_management.entity.Lift;
import com.kamilwnek.lift_management.mapper.LiftMapper;
import com.kamilwnek.lift_management.repository.LiftRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LiftService {

    private final LiftRepository liftRepository;
    private final LiftMapper liftMapper;

    public LiftDto createLift(LiftDto liftRequest) {
        Lift lift = liftRepository.save(liftMapper.toEntity(liftRequest));
        return liftMapper.toDto(lift);
    }

    public Lift getLiftById(Long id) {
        return liftRepository.findById(id).get();
    }
}
