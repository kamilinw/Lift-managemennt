package com.kamilwnek.lift_management.validator;

import com.kamilwnek.lift_management.entity.Lift;
import com.kamilwnek.lift_management.service.LiftService;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueUdtNumberValidator implements ConstraintValidator<UniqueUdtNumber, String> {

    private final LiftService liftService;

    @Override
    public boolean isValid(String udtNumber, ConstraintValidatorContext constraintValidatorContext) {
        Lift lift = liftService.findLiftByUdtNumber(udtNumber);
        return lift == null;
    }
}
