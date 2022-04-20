package com.kamilwnek.lift_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamilwnek.lift_management.dto.BuildingDto;
import com.kamilwnek.lift_management.dto.LiftDto;
import com.kamilwnek.lift_management.entity.Lift;
import com.kamilwnek.lift_management.entity.User;
import com.kamilwnek.lift_management.exception.NoSuchRecordException;
import com.kamilwnek.lift_management.mapper.LiftMapper;
import com.kamilwnek.lift_management.security.JwtTokenUtil;
import com.kamilwnek.lift_management.service.LiftService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource( locations = "/application-test.properties")
class LiftControllerTest {

    @MockBean
    private LiftService liftService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private Clock clock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LiftMapper liftMapper;

    private String token;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        User user = new User("username", "password", "test@email.com");
        user.setId(1L);
        token = "Bearer " + jwtTokenUtil.createAccessToken(user, clock);
    }

    @Test
    void createLift_validBody_statusCreated() throws Exception{
        BuildingDto buildingDto = new BuildingDto(1L, "Building name", "City", "address");
        LiftDto liftDto = new LiftDto(
                1L,
                buildingDto,
                "Serial Number",
                "Udt Number",
                "Activation date",
                "Comment"
        );
        given(liftService.createLift(liftDto)).willReturn(liftMapper.toEntity(liftDto));

        mockMvc.perform(
                post("/api/v1/lift/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(liftDto))
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isCreated());
    }

    @Test
    void createLift_nonexistentBuilding_statusNotFound() throws Exception{
        BuildingDto buildingDto = new BuildingDto(1L, null, null, null);
        LiftDto liftDto = new LiftDto(
                1L,
                buildingDto,
                "Serial Number",
                "Udt Number",
                "Activation date",
                "Comment"
        );
        given(liftService.createLift(any())).willThrow(new NoSuchRecordException("Building with id 1 not found"));

        mockMvc.perform(
                post("/api/v1/lift")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(liftDto))
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound());
    }


    @Test
    void getLiftById_validId_statusOk() throws Exception{
        given(liftService.getLiftById(anyLong())).willReturn(new Lift());

        mockMvc.perform(
                get("/api/v1/lift/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk());
    }

    @Test
    void getLiftById_invalidId_statusNotFound() throws Exception{
        given(liftService.getLiftById(anyLong())).willThrow(new NoSuchRecordException("Lift with id 2 not found"));

        mockMvc.perform(
                get("/api/v1/lift/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound());
    }
}