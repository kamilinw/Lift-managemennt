package com.kamilwnek.lift_management.repository;

import com.kamilwnek.lift_management.entity.Building;
import com.kamilwnek.lift_management.entity.Lift;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LiftRepositoryTest {
    @Autowired
    private LiftRepository underTest;
    @Autowired
    private BuildingRepository buildingRepository;
    private Building building;
    private String udtNumber;
    private String serialNumber;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @BeforeEach
    void setUp() {
        building = new Building("Szkoła", "Rzeszów", "Mickiewicza 12");
        buildingRepository.save(building);
        udtNumber = "248dfg337412a";
        serialNumber = "00:1B:44:11:3A:B7";
    }

    @Test
    void isTheSameUdtNumberNotUnique() {
        //given
        Lift lift = new Lift(
                building,
                serialNumber,
                udtNumber,
                "activationDate",
                "comment");
        underTest.save(lift);
        //when
        Boolean actual = underTest.isUdtNumberUnique(udtNumber);
        //than
        assertThat(!actual);
    }

    @Test
    void isTheSameSerialNumberNotUnique() {
        //given
        Lift lift = new Lift(
                building,
                serialNumber,
                udtNumber,
                "activationDate",
                "comment");
        underTest.save(lift);
        //when
        Boolean actual = underTest.isSerialNumberUnique(serialNumber);
        //than
        assertThat(!actual);
    }
}