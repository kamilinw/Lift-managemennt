package com.kamilwnek.lift_management.service;

import com.kamilwnek.lift_management.dto.BuildingDto;
import com.kamilwnek.lift_management.dto.LiftDto;
import com.kamilwnek.lift_management.entity.Building;
import com.kamilwnek.lift_management.entity.Lift;
import com.kamilwnek.lift_management.exception.NoSuchRecordException;
import com.kamilwnek.lift_management.exception.ValidationException;
import com.kamilwnek.lift_management.mapper.BuildingMapper;
import com.kamilwnek.lift_management.mapper.LiftMapper;
import com.kamilwnek.lift_management.repository.BuildingRepository;
import com.kamilwnek.lift_management.repository.LiftRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LiftServiceTest {

    private LiftService underTest;
    private BuildingDto buildingDto;
    private LiftDto liftDto;
    @Mock private LiftRepository liftRepository;
    @Mock private BuildingRepository buildingRepository;
    @Mock private LiftMapper liftMapper;
    @Mock private BuildingMapper buildingMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new LiftService(
                liftRepository,
                buildingRepository,
                liftMapper,
                buildingMapper);
        buildingDto = new BuildingDto(
                1L,
                "name",
                "city",
                "address"
        );
        liftDto = new LiftDto();
        liftDto.setBuildingDto(buildingDto);
    }

    @Test
    void canCreateLiftAndCreateNewBuilding() {
        //given
        liftDto.getBuildingDto().setId(null);

        //when
        underTest.createLift(liftDto);
        //then
        ArgumentCaptor<Building> buildingArgumentCaptor = ArgumentCaptor.forClass(Building.class);
        verify(buildingRepository).save(buildingArgumentCaptor.capture());
        Building capturedBuilding = buildingArgumentCaptor.getValue();
        assertThat(capturedBuilding).isEqualTo(buildingMapper.toEntity(buildingDto));

        ArgumentCaptor<Lift> liftArgumentCaptor = ArgumentCaptor.forClass(Lift.class);
        verify(liftRepository).save(liftArgumentCaptor.capture());
        Lift capturedLift = liftArgumentCaptor.getValue();
        assertThat(capturedLift).isEqualTo(liftMapper.toEntity(liftDto));
    }

    @Test
    void canFindExistingBuildingAndCreateNewLift(){
        //given
        liftDto.getBuildingDto().setName(null);
        liftDto.getBuildingDto().setAddress(null);
        liftDto.getBuildingDto().setCity(null);
        Building building = new Building();
        building.setId(1L);
        given(buildingRepository.findById(building.getId())).willReturn(Optional.of(building));
        //when
        underTest.createLift(liftDto);

        //then
        ArgumentCaptor<Lift> liftArgumentCaptor = ArgumentCaptor.forClass(Lift.class);
        verify(liftRepository).save(liftArgumentCaptor.capture());
        Lift capturedLift = liftArgumentCaptor.getValue();
        assertThat(capturedLift).isEqualTo(liftMapper.toEntity(liftDto));
    }

    @Test
    void willThrowExceptionWhenBuildingIdNotFound(){
        //given
        liftDto.getBuildingDto().setName(null);
        liftDto.getBuildingDto().setAddress(null);
        liftDto.getBuildingDto().setCity(null);
        given(buildingRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> underTest.createLift(liftDto))
                .isInstanceOf(NoSuchRecordException.class)
                .hasMessageContaining("Building with id " + liftDto.getBuildingDto().getId() + " not found");
    }

    @Test
    void willThrowWhenCityIsNull(){
        //given
        liftDto.getBuildingDto().setId(null);
        liftDto.getBuildingDto().setCity(null);

        //when
        //then
        assertThatThrownBy(() -> underTest.createLift(liftDto))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("city can not be null!");
        verify(buildingRepository, never()).save(any());
    }

    @Test
    void willThrowWhenAddressIsNull(){
        //given
        liftDto.getBuildingDto().setId(null);
        liftDto.getBuildingDto().setAddress(null);

        //when
        //then
        assertThatThrownBy(() -> underTest.createLift(liftDto))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("address can not be null!");
        verify(buildingRepository, never()).save(any());
    }

    @Test
    void willThrowWhenNameIsNull(){
        //given
        liftDto.getBuildingDto().setId(null);
        liftDto.getBuildingDto().setName(null);

        //when
        //then
        assertThatThrownBy(() -> underTest.createLift(liftDto))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("name can not be null!");
        verify(buildingRepository, never()).save(any());
    }

    @Test
    void canGetLiftById() {
        //given
        Lift lift = new Lift();
        lift.setId(57L);
        given(liftRepository.findById(lift.getId())).willReturn(Optional.of(lift));
        //when
        Lift actual = underTest.getLiftById(lift.getId());
        //then
        assertThat(actual).isSameAs(lift);
        verify(liftRepository).findById(lift.getId());
    }

    @Test
    void willThrowWhenLiftDoesNotExist(){
        //given
        given(liftRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> underTest.getLiftById(anyLong()))
                .isInstanceOf(NoSuchRecordException.class)
                .hasMessageContaining("Lift with id");
    }
}