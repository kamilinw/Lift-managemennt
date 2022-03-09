package com.kamilwnek.lift_management.repository;

import com.kamilwnek.lift_management.entity.RefreshToken;
import com.kamilwnek.lift_management.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.Instant;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository underTest;

    @Test
    void findByTokenAndDeviceName() {
        //given
        User user = new User("kowalski", "password", "kowalski@email.com");
        String token = "f81d4fae-7dec-11d0-a765-00a0c91e6bf6";
        String deviceName = "Notebook";
        RefreshToken refreshToken = new RefreshToken(1L, user, token, LocalDateTime.now(), deviceName, LocalDateTime.now(), LocalDateTime.now());
        underTest.save(refreshToken);
        //when
        RefreshToken actual = underTest.findByTokenAndDeviceName(token, deviceName).get();
        //then
        assertThat(actual.getToken()).isEqualTo(token);
        assertThat(actual.getDeviceName()).isEqualTo(deviceName);
    }
}