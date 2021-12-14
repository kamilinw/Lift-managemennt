package com.kamilwnek.lift_management.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
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
    @Column(nullable = false)
    private Long objectId;
    @Column(nullable = false)
    private String serialNumber;
    @Column(nullable = false)
    private String udtNumber;
    @Column(nullable = false)
    private String activationDate;
    private String comment;

    public Lift(Long objectId, String serialNumber, String udtNumber, String activationDate, String comment) {
        this.objectId = objectId;
        this.serialNumber = serialNumber;
        this.udtNumber = udtNumber;
        this.activationDate = activationDate;
        this.comment = comment;
    }
}
