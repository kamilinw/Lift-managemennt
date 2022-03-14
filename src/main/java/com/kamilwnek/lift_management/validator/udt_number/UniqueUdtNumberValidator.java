package com.kamilwnek.lift_management.validator.udt_number;

import com.kamilwnek.lift_management.repository.LiftRepository;
import lombok.AllArgsConstructor;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueUdtNumberValidator implements ConstraintValidator<UniqueUdtNumber, String> {

    private final LiftRepository liftRepository;

    @Override
    public boolean isValid(String udtNumber, ConstraintValidatorContext constraintValidatorContext) {
        return liftRepository.isUdtNumberUnique(udtNumber);
    }
}
