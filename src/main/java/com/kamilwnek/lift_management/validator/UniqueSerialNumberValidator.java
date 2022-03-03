package com.kamilwnek.lift_management.validator;

import com.kamilwnek.lift_management.repository.LiftRepository;
import lombok.AllArgsConstructor;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueSerialNumberValidator implements ConstraintValidator<UniqueSerialNumber, String> {

    private final LiftRepository liftRepository;

    @Override
    public boolean isValid(String serialNumber, ConstraintValidatorContext constraintValidatorContext) {
        return liftRepository.isSerialNumberUnique(serialNumber);
    }
}
