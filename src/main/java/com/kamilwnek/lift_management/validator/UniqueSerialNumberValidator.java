package com.kamilwnek.lift_management.validator;

import com.kamilwnek.lift_management.entity.Lift;
import com.kamilwnek.lift_management.service.LiftService;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueSerialNumberValidator implements ConstraintValidator<UniqueSerialNumber, String> {

    private final LiftService liftService;

    @Override
    public boolean isValid(String serialNumber, ConstraintValidatorContext constraintValidatorContext) {
        Lift lift = liftService.findLiftBySerialNumber(serialNumber);
        return lift == null;
    }
}
