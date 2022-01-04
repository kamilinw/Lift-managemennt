package com.kamilwnek.lift_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Setter
@Getter
public class LiftDto {

    @Id
    private Long id;
    @NotNull(message = "Building id nie moze być puste!")
    private BuildingDto buildingDto;
    @NotNull
    private String serialNumber;
    @NotNull
    private String udtNumber;
    @NotNull
    private String activationDate;
    private String comment;
}
