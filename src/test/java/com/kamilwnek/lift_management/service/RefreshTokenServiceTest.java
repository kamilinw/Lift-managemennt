package com.kamilwnek.lift_management.service;

import com.kamilwnek.lift_management.dto.RefreshTokenRequest;
import com.kamilwnek.lift_management.dto.RefreshTokenResponse;
import com.kamilwnek.lift_management.entity.RefreshToken;
import com.kamilwnek.lift_management.entity.User;
import com.kamilwnek.lift_management.exception.JwtTokenException;
import com.kamilwnek.lift_management.repository.RefreshTokenRepository;
import com.kamilwnek.lift_management.security.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

    private RefreshTokenService underTest;
    @Mock private RefreshTokenRepository refreshTokenRepository;
    @Mock private JwtTokenUtil jwtTokenUtil;
    private Clock fixedClock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        underTest = new RefreshTokenService(refreshTokenRepository, jwtTokenUtil, fixedClock);
    }

    @Test
    void canCreateNewRefreshToken() {
        //given
        User user = new User();
        String deviceName = "device name";
        //when
        underTest.createToken(user, deviceName);
        //then
        ArgumentCaptor<RefreshToken> refreshTokenArgumentCaptor = ArgumentCaptor.forClass(RefreshToken.class);
        verify(refreshTokenRepository).save(refreshTokenArgumentCaptor.capture());
        RefreshToken capturedRefreshToken = refreshTokenArgumentCaptor.getValue();

        assertThat(capturedRefreshToken.getDeviceName()).isEqualTo(deviceName);
        assertThat(capturedRefreshToken.getUser()).isEqualTo(user);
    }

    @Test
    void canRefreshTokenWhenValidRequestToken() {
        //given
        String deviceName = "Device name";
        User user = new User();
        String token = "token";
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .expiryDate(LocalDateTime.now(Clock.offset(fixedClock, Duration.ofHours(2))))
                .token(token)
                .deviceName(deviceName)
                .build();
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setRefreshToken(token);
        given(refreshTokenRepository.findByTokenAndDeviceName(token, deviceName))
                .willReturn(Optional.of(refreshToken));
        //when
        RefreshTokenResponse actualRefreshTokenResponse = underTest.refreshToken(refreshTokenRequest, deviceName);
        //then

        verify(jwtTokenUtil).createAccessToken(user, fixedClock);
        assertThat(actualRefreshTokenResponse.getRefreshToken()).isNotEqualTo(token);
    }

    @Test
    void willThrowExceptionWhenRequestWrongRefreshToken(){
        //given
        String deviceName = "Device name";
        String token = "token";
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setRefreshToken(token);
        given(refreshTokenRepository.findByTokenAndDeviceName(token, deviceName)).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> underTest.refreshToken(refreshTokenRequest, deviceName))
                .isInstanceOf(JwtTokenException.class)
                .hasMessage("Wrong Refresh Token!");

    }

    @Test
    void willThrowExceptionWhenRequestedRefreshTokenIsExpired(){
        //given
        String deviceName = "Device name";
        User user = new User();
        String token = "token";
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .expiryDate(LocalDateTime.now(Clock.offset(fixedClock, Duration.ofHours(-2))))
                .token(token)
                .deviceName(deviceName)
                .build();
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setRefreshToken(token);
        given(refreshTokenRepository.findByTokenAndDeviceName(token, deviceName))
                .willReturn(Optional.of(refreshToken));
        //then
        //when
        assertThatThrownBy(() -> underTest.refreshToken(refreshTokenRequest, deviceName))
                .isInstanceOf(JwtTokenException.class)
                .hasMessage("Refresh token was expired. Please make a new sign in request");
        verify(refreshTokenRepository).delete(any(RefreshToken.class));
    }
}