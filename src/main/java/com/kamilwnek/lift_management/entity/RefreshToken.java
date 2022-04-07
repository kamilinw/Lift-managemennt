package com.kamilwnek.lift_management.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
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
    private LocalDateTime expiryDate;
    private String deviceName;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public RefreshToken(){

    }
}
