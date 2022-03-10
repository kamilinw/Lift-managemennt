package com.kamilwnek.lift_management.service;

import com.kamilwnek.lift_management.entity.Lift;
import com.kamilwnek.lift_management.exception.NoSuchRecordException;
import com.kamilwnek.lift_management.mapper.BuildingMapper;
import com.kamilwnek.lift_management.mapper.LiftMapper;
import com.kamilwnek.lift_management.repository.BuildingRepository;
import com.kamilwnek.lift_management.repository.LiftRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
    @Mock private LiftRepository liftRepository;
    @Mock private BuildingRepository buildingRepository;
    @Mock private LiftMapper liftMapper;
    @Mock private BuildingMapper buildingMapper;

    @BeforeEach
    void setUp() {
        underTest = new LiftService(
                liftRepository,
                buildingRepository,
                liftMapper,
                buildingMapper);
    }

    @Test
    @Disabled
    void createLift() {
    }

    @Test
    void canGetLiftById() {
        //given
        Lift lift = new Lift();
        lift.setId(57L);
        //when
        when(liftRepository.findById(lift.getId())).thenReturn(Optional.of(lift));
        //then
        Lift expected = underTest.getLiftById(lift.getId());
        assertThat(expected).isSameAs(lift);
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