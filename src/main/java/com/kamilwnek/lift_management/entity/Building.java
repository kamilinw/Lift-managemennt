package com.kamilwnek.lift_management.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Building {

    @Id
    @SequenceGenerator(
            name = "object_seq",
            sequenceName = "object_seq",
            allocationSize = 1)
    @GeneratedValue(
            generator = "object_seq",
            strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String city;
    private String address;

    public Building(String name, String city, String address) {
        this.name = name;
        this.city = city;
        this.address = address;
    }
}
