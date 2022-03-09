package com.kamilwnek.lift_management.service;

import com.kamilwnek.lift_management.dto.BuildingDto;
import com.kamilwnek.lift_management.entity.Building;
import com.kamilwnek.lift_management.mapper.BuildingMapper;
import com.kamilwnek.lift_management.repository.BuildingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BuildingServiceTest {

    @Mock private BuildingRepository buildingRepository;
    @Mock private BuildingMapper buildingMapper;
    private BuildingService underTest;

    @BeforeEach
    void setUp() {
        underTest = new BuildingService(buildingRepository, buildingMapper);
    }

    @Test
    void canCreateNewBuilding() {
        //given
        BuildingDto buildingDto = new BuildingDto(1L, "building name", "City", "building address");
        // when
        underTest.createBuilding(buildingDto);
        //then
        ArgumentCaptor<Building> buildingArgumentCaptor = ArgumentCaptor.forClass(Building.class);
        verify(buildingRepository).save(buildingArgumentCaptor.capture());
        Building capturedBuilding = buildingArgumentCaptor.getValue();

        assertThat(capturedBuilding).isEqualTo(buildingMapper.toEntity(buildingDto));
    }
}