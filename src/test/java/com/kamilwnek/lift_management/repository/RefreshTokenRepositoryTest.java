package com.kamilwnek.lift_management.repository;

import com.kamilwnek.lift_management.entity.RefreshToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository underTest;
    private final String token = "f81d4fae-7dec-11d0-a765-00a0c91e6bf6";
    private final String deviceName = "Notebook";

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findByTokenAndDeviceName() {
        //given
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setDeviceName(deviceName);
        refreshToken.setToken(token);
        underTest.save(refreshToken);
        //when
        RefreshToken actual = underTest.findByTokenAndDeviceName(token, deviceName).get();
        //then
        assertThat(actual.getToken()).isEqualTo(token);
        assertThat(actual.getDeviceName()).isEqualTo(deviceName);
    }
}