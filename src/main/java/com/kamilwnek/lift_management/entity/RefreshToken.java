package com.kamilwnek.lift_management.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Builder
public class RefreshToken {
    @Id
    @SequenceGenerator(
            name = "refresh_token_seq",
            sequenceName = "refresh_token_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "refresh_token_seq"
    )
    private Long id;

    @ManyToOne(
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            targetEntity = User.class
    )
    @JoinColumn(
            name = "user_id"
    )
    private User user;

    private String token;

    private Instant expiryDate;

    private String deviceName;
}
