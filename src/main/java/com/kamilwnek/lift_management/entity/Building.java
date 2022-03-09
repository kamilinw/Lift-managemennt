package com.kamilwnek.lift_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

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
    @OneToMany(
            mappedBy = "building"
    )
    @JsonIgnore
    private Set<Lift> lifts;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Building(Long id, String name, String city, String address) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
    }

    public Building(String name, String city, String address) {
        this.name = name;
        this.city = city;
        this.address = address;
    }
}
