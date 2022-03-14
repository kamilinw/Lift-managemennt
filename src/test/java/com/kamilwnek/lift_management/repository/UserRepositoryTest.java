package com.kamilwnek.lift_management.repository;

import com.kamilwnek.lift_management.entity.User;
import com.kamilwnek.lift_management.enums.ApplicationUserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;
    private User user;
    private final String username = "username";
    private final String email = "test@email.com";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername(username);
        user.setPassword("pass");
        user.setApplicationUserRole(ApplicationUserRole.EMPLOYEE);
        user.setEnabled(true);
        user.setEmail(email);
        underTest.save(user);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void isTheSameUsernameNotUnique() {
        //when
        Boolean actual = underTest.isUsernameUnique(username);
        //than
        assertThat(actual).isFalse();
    }

    @Test
    void isNewUsernameUnique(){
        //when
        Boolean actual = underTest.isUsernameUnique("new username");
        //than
        assertThat(actual).isTrue();
    }

    @Test
    void isTheSameEmailNotUnique() {
        //when
        Boolean actual = underTest.isEmailUnique(email);
        //than
        assertThat(actual).isFalse();
    }

    @Test
    void isNewEmailUnique(){
        //when
        Boolean actual = underTest.isEmailUnique("new email");
        //than
        assertThat(actual).isTrue();
    }
}