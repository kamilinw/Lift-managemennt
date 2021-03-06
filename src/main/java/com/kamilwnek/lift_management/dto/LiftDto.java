package com.kamilwnek.lift_management.dto;

import com.kamilwnek.lift_management.validator.serial_number.UniqueSerialNumber;
import com.kamilwnek.lift_management.validator.udt_number.UniqueUdtNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LiftDto {

    @Id
    private String id;
    @NotNull
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
