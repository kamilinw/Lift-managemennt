package com.kamilwnek.lift_management.validator.serial_number;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueSerialNumberValidator.class)
public @interface UniqueSerialNumber {
    String message() default "This serial number already exists!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
