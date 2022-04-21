package com.kamilwnek.lift_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamilwnek.lift_management.dto.UserDto;
import com.kamilwnek.lift_management.entity.User;
import com.kamilwnek.lift_management.exception.NoSuchRecordException;
import com.kamilwnek.lift_management.mapper.UserMapper;
import com.kamilwnek.lift_management.security.JwtTokenUtil;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Clock;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource( locations = "/application-test.properties")
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private Clock clock;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

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
    void getUser_validToken_statusOk() throws Exception{
        UserDto userDto = userMapper.toDto(user);
        given(userService.getUserById(any())).willReturn(userDto);

        MvcResult mvcResult = mockMvc.perform(
                get("/api/v1/user")
                    .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String expectedResponseBody = objectMapper.writeValueAsString(userDto);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void getUser_invalidUser_statusNotFound() throws Exception{
        given(userService.getUserById(any())).willThrow(new NoSuchRecordException("User with id %s not found!"));

        mockMvc.perform(
                get("/api/v1/user")
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound());
    }
}