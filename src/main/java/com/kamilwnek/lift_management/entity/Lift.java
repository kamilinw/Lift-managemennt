package com.kamilwnek.lift_management.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private Long objectId;
    private String serialNumber;
    private String UDTNumber;
    private Date activationDate;
    private String comment;

    public Lift(Long objectId, String serialNumber, String UDTNumber, Date activationDate, String comment) {
        this.objectId = objectId;
        this.serialNumber = serialNumber;
        this.UDTNumber = UDTNumber;
        this.activationDate = activationDate;
        this.comment = comment;
    }
}
