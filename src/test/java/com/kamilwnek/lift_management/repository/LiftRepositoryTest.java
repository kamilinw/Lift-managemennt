package com.kamilwnek.lift_management.repository;

import com.kamilwnek.lift_management.entity.Building;
import com.kamilwnek.lift_management.entity.Lift;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindLiftByUdtNumber() {
        //given
        String udtNumber = "248dfg337412a";
        Building building = new Building("Szkoła", "Rzeszów", "Mickiewicza 12");
        buildingRepository.save(building);
        Lift lift = new Lift(
                building,
                "serialNumber",
                udtNumber,
                "activationDate",
                "comment");
        underTest.save(lift);
        //when
        Lift expected = underTest.findLiftByUdtNumber(udtNumber);
        //than
        assertThat(expected.getUdtNumber()).isEqualTo(udtNumber);
    }
}