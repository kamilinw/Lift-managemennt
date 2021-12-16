package com.kamilwnek.lift_management.repository;


import com.kamilwnek.lift_management.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
}
