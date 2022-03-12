package com.kamilwnek.lift_management.repository;

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
    private final String udtNumber = "UDT number";
    private final String serialNumber = "serial number";

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @BeforeEach
    void setUp() {
       Lift lift = new Lift();
       lift.setUdtNumber(udtNumber);
       lift.setSerialNumber(serialNumber);
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