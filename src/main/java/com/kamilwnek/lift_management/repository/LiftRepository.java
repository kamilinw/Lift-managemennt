package com.kamilwnek.lift_management.repository;

import com.kamilwnek.lift_management.entity.Lift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LiftRepository extends JpaRepository<Lift, UUID> {

    @Query(
            "SELECT " +
                "CASE " +
                    "WHEN COUNT(id) = 0 THEN TRUE " +
                    "ELSE FALSE " +
                "END " +
            "FROM Lift l " +
            "WHERE l.udtNumber = ?1"
    )
    Boolean isUdtNumberUnique(String udtNumber);

    @Query(
            "SELECT " +
                "CASE " +
                    "WHEN COUNT(id) = 0 THEN TRUE " +
                    "ELSE FALSE " +
                "END " +
            "FROM Lift l " +
            "WHERE l.serialNumber = ?1"
    )
    Boolean isSerialNumberUnique(String serialNumber);
}
