package com.kamilwnek.lift_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class CreateBuildingRequest {

    @NotNull
    private String name;
    @NotNull
    private String city;
    @NotNull
    private String address;
}
