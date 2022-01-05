package com.kamilwnek.lift_management.repository;

import com.kamilwnek.lift_management.entity.Lift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LiftRepository extends JpaRepository<Lift, Long> {

    @Query("SELECT l FROM Lift l " +
            "WHERE l.udtNumber = ?1")
    Lift findLiftByUdtNumber(String udtNumber);

    @Query("SELECT l FROM Lift l " +
            "WHERE l.serialNumber = ?1")
    Lift findLiftBySerialNumber(String serialNumber);
}
