package com.kamilwnek.lift_management.service;

import com.kamilwnek.lift_management.dto.CreateUserRequest;
import com.kamilwnek.lift_management.dto.LoginRequest;
import com.kamilwnek.lift_management.entity.RefreshToken;
import com.kamilwnek.lift_management.entity.User;
import com.kamilwnek.lift_management.enums.ApplicationUserRole;
import com.kamilwnek.lift_management.exception.NoSuchRecordException;
import com.kamilwnek.lift_management.mapper.UserMapper;
import com.kamilwnek.lift_management.repository.UserRepository;
import com.kamilwnek.lift_management.security.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.ValidationException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService underTest;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private UserRepository userRepository;
    @Mock private RefreshTokenService refreshTokenService;
    @Mock private UserMapper userMapper;
    @Mock private JwtTokenUtil jwtTokenUtil;
    private Clock clock;

    @BeforeEach
    void setUp(){
        clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        underTest = new UserService(
                authenticationManager,
                userRepository,
                refreshTokenService,
                userMapper,
                jwtTokenUtil,
                clock);
    }

    @Test
    void canLoadUserByUsername(){
        //given
        String username = "username";
        User user = new User();
        //when
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        underTest.loadUserByUsername(username);
        //then
        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository).findByUsername(usernameArgumentCaptor.capture());
        String actual = usernameArgumentCaptor.getValue();
        assertThat(actual).isEqualTo(username);
    }

    @Test
    void willThrowExceptionWhenUsernameNotFound(){
        //given
        String username = "username";
        given(userRepository.findByUsername(username)).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> underTest.loadUserByUsername(username))
                .isInstanceOf(NoSuchRecordException.class)
                .hasMessageContaining("User with username")
                .hasMessageContaining("doesn't exist!");
    }

    @Test
    void canCreateUser(){
        //given
        CreateUserRequest request = new CreateUserRequest(
                "username",
                "test@email.com",
                "password",
                "password"
        );

        //when
        underTest.create(request);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User actualUser = userArgumentCaptor.getValue();

        assertThat(actualUser).isEqualTo(userMapper.toEntity(request));
    }

    @Test
    void willThrowExceptionWhenPasswordsDoesNotMatch(){
        //given
        CreateUserRequest request = new CreateUserRequest(
                "username",
                "test@email.com",
                "password",
                "different password"
        );
        //when
        //then
        assertThatThrownBy(() -> underTest.create(request))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Passwords don't match!");
        verify(userRepository, never()).save(any());
    }

    @Test
    void canLoginUser(){
        //given
        String username = "username";
        String password = "password";
        String deviceName = "device name";
        String accessToken = "access token";

        RefreshToken refreshToken = new RefreshToken();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setApplicationUserRole(ApplicationUserRole.EMPLOYEE);
        user.setEnabled(true);

        //when
        when(authenticationManager.authenticate(any()))
                .thenReturn(
                        new UsernamePasswordAuthenticationToken(
                                user,
                                user.getPassword(),
                                user.getAuthorities())
                );
        when(jwtTokenUtil.createAccessToken(user, clock)).thenReturn(accessToken);
        when(refreshTokenService.createToken(user, deviceName)).thenReturn(refreshToken);
        underTest.loginUser(loginRequest, deviceName);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<RefreshToken> refreshTokenArgumentCaptor = ArgumentCaptor.forClass(RefreshToken.class);
        ArgumentCaptor<String> accessTokenArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userMapper).toLoginResponse(
                userArgumentCaptor.capture(),
                refreshTokenArgumentCaptor.capture(),
                accessTokenArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        RefreshToken capturedRefreshToken = refreshTokenArgumentCaptor.getValue();
        String capturedAccessToken = accessTokenArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
        assertThat(capturedAccessToken).isEqualTo(accessToken);
        assertThat(capturedRefreshToken).isEqualTo(refreshToken);
    }

    @Test
    void willThrowExceptionWhenBadCredentialsInLogIn(){
        //given
        String username = "username";
        String password = "password";
        String deviceName = "device name";

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);
        given(authenticationManager.authenticate(any())).willThrow(BadCredentialsException.class);

        //when
        //then
        assertThatThrownBy(() -> underTest.loginUser(loginRequest, deviceName))
                .isInstanceOf(BadCredentialsException.class);

        verify(refreshTokenService, never()).createToken(any(), anyString());
        verify(jwtTokenUtil, never()).createAccessToken(any(), any());
        verify(userMapper, never()).toLoginResponse(any(), any(), anyString());
    }

    @Test
    void canGetUserById(){
        //given
        Long id = 1L;
        //when
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        underTest.getUserById(id);
        //then
        ArgumentCaptor<Long> userIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(userRepository).findById(userIdArgumentCaptor.capture());
        Long actualId = userIdArgumentCaptor.getValue();

        assertThat(actualId).isEqualTo(id);
    }

    @Test
    void willThrowExceptionWhenUserWithGivenIdNotFound(){
        //given
        Long id = 1L;
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> underTest.getUserById(id))
                .isInstanceOf(NoSuchRecordException.class)
                .hasMessageContaining("User with id")
                .hasMessageContaining("not found!");
    }
}