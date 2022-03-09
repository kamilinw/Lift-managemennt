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
    private String udtNumber;
    private String serialNumber;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @BeforeEach
    void setUp() {
        Building building = new Building("Szkoła", "Rzeszów", "Mickiewicza 12");
        buildingRepository.save(building);
        udtNumber = "248dfg337412a";
        serialNumber = "00:1B:44:11:3A:B7";
        Lift lift = new Lift(
                building,
                serialNumber,
                udtNumber,
                "activationDate",
                "comment");
        underTest.save(lift);
    }

    @Test
    void isTheSameUdtNumberNotUnique() {
        //when
        Boolean actual = underTest.isUdtNumberUnique(udtNumber);
        //than
        assertThat(!actual);
    }

    @Test
    void isNewUdtNumberUnique(){
        //when
        Boolean actual = underTest.isUdtNumberUnique("otherUdtNumber");
        //than
        assertThat(actual);
    }

    @Test
    void isTheSameSerialNumberNotUnique() {
        //when
        Boolean actual = underTest.isSerialNumberUnique(serialNumber);
        //than
        assertThat(!actual);
    }

    @Test
    void isNewSerialNumberUnique(){
        //when
        Boolean actual = underTest.isSerialNumberUnique(serialNumber);
        //then
        assertThat(actual);
    }
}