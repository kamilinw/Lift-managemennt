package com.kamilwnek.lift_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Setter
@Getter
public class CreateLiftRequest {
    @NotNull(message = "Object id nie może być puste idioto!")
    private Long objectId;
    @NotNull
    private String serialNumber;
    @NotNull
    private String udtNumber;
    @NotNull
    private String activationDate;
    private String comment;
}
