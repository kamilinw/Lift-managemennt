package com.kamilwnek.lift_management.validator.udt_number;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUdtNumberValidator.class)
public @interface UniqueUdtNumber {
    String message() default "This UDT number already exists!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
