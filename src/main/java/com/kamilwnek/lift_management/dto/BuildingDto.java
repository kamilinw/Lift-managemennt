package com.kamilwnek.lift_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class BuildingDto {

    @Id
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String city;
    @NotNull
    private String address;
}
