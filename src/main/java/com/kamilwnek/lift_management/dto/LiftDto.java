package com.kamilwnek.lift_management.dto;

import com.kamilwnek.lift_management.validator.UniqueSerialNumber;
import com.kamilwnek.lift_management.validator.UniqueUdtNumber;
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
    @UniqueSerialNumber
    private String serialNumber;
    @NotNull
    @UniqueUdtNumber
    private String udtNumber;
    @NotNull
    private String activationDate;
    private String comment;
}
