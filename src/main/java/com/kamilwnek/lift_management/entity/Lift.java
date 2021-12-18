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
public class Lift {

    @Id
    @SequenceGenerator(
            name = "lift_seq",
            sequenceName = "lift_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lift_seq"
    )
    private Long id;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "building_id"
    )
    private Building building;
    @Column(nullable = false)
    private String serialNumber;
    @Column(nullable = false)
    private String udtNumber;
    @Column(nullable = false)
    private String activationDate;
    private String comment;

    public Lift(Building building, String serialNumber, String udtNumber, String activationDate, String comment) {
        this.building = building;
        this.serialNumber = serialNumber;
        this.udtNumber = udtNumber;
        this.activationDate = activationDate;
        this.comment = comment;
    }
}
