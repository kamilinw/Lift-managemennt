package com.kamilwnek.lift_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamilwnek.lift_management.dto.*;
import com.kamilwnek.lift_management.entity.User;
import com.kamilwnek.lift_management.exception.JwtTokenException;
import com.kamilwnek.lift_management.exception.ValidationException;
import com.kamilwnek.lift_management.mapper.UserMapper;
import com.kamilwnek.lift_management.security.JwtTokenUtil;
import com.kamilwnek.lift_management.service.RefreshTokenService;
import com.kamilwnek.lift_management.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.time.Clock;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource( locations = "/application-test.properties")
class AuthControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private RefreshTokenService refreshTokenService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private Clock clock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserMapper userMapper;

    private String token;
    private MockMvc mockMvc;
    private User user;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        user = new User("username", "password", "test@email.com");
        user.setId(UUID.randomUUID());
        token = "Bearer " + jwtTokenUtil.createAccessToken(user, clock);
    }

    @Test
    void login_validBody_statusOk() throws Exception{
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        given(userService.loginUser(any(),anyString()))
                .willReturn(
                        new LoginResponse(
                                userMapper.toDto(user),
                                token,
                                "refresh token",
                                "Bearer "));

        mockMvc.perform(
                post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))
                .header(HttpHeaders.USER_AGENT, "device name"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void login_invalidBody_statusBadRequest() throws Exception{
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");

        mockMvc.perform(
                post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_validCredentials_statusCreated() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest(
                "username",
                "email@test.com",
                "password",
                "password"
        );

        given(userService.create(any())).willReturn(userMapper.toDto(user));

        mockMvc.perform(
                post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest))
                        .header(HttpHeaders.USER_AGENT, "device name"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void register_invalidCredentials_statusBadRequest() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest(
                "username",
                "email@test.com",
                "password",
                "password don't match"
        );

        given(userService.create(any())).willThrow(ValidationException.class);

        mockMvc.perform(
                post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest))
                        .header(HttpHeaders.USER_AGENT, "device name"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void refreshToken_validToken_statusIsOk() throws Exception {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setRefreshToken("refresh token");

        given(refreshTokenService.refreshToken(any(), anyString()))
                .willReturn(
                        new RefreshTokenResponse(
                                "access token",
                                "refresh token",
                                "Token type"));

        mockMvc.perform(
                post("/api/v1/auth/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(refreshTokenRequest))
                        .header(HttpHeaders.USER_AGENT, "device name"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void refreshToken_wrongToken_statusUnauthorized() throws Exception {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setRefreshToken("wrong refresh token");

        given(refreshTokenService.refreshToken(any(), anyString()))
                .willThrow(JwtTokenException.class);

        mockMvc.perform(
                post("/api/v1/auth/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(refreshTokenRequest))
                        .header(HttpHeaders.USER_AGENT, "device name"))
                .andExpect(status().isUnauthorized());
    }
}