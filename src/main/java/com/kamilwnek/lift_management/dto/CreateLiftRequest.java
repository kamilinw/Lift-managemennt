package com.kamilwnek.lift_management.dto;

import com.kamilwnek.lift_management.entity.Building;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Setter
@Getter
public class CreateLiftRequest {
    @NotNull(message = "Object id nie może być puste idioto!")
    private Building building;
    @NotNull
    private String serialNumber;
    @NotNull
    private String udtNumber;
    @NotNull
    private String activationDate;
    private String comment;
}
