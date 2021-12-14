package com.kamilwnek.lift_management.repository;

import com.kamilwnek.lift_management.entity.Lift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiftRepository extends JpaRepository<Lift, Long> {
}
