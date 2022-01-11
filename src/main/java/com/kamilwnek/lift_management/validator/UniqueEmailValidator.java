package com.kamilwnek.lift_management.validator;

import com.kamilwnek.lift_management.exception.NoSuchRecordException;
import com.kamilwnek.lift_management.service.UserService;
import lombok.RequiredArgsConstructor;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
       try{
           userService.loadUserByUsername(email);
       } catch (NoSuchRecordException e){
           return true;
       }
        return false;
    }
}
