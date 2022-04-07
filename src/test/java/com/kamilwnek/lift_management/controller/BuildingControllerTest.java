package com.kamilwnek.lift_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamilwnek.lift_management.entity.Building;
import com.kamilwnek.lift_management.entity.User;
import com.kamilwnek.lift_management.security.JwtTokenUtil;
import com.kamilwnek.lift_management.service.BuildingService;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource( locations = "/application-test.properties")
class BuildingControllerTest {

    @MockBean
    private BuildingService buildingService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private Clock clock;

    @Autowired
    private ObjectMapper objectMapper;

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
    void createBuilding_validBody_statusCreated() throws Exception{
        Building building = new Building("building name", "city", "address");
        given(buildingService.createBuilding(any())).willReturn(building);

        mockMvc.perform(
                post("/api/v1/building/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(building))
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createBuilding_invalidBody_statusBadRequest() throws Exception{
        Building building = new Building("building name", null, "address");

        mockMvc.perform(
                post("/api/v1/building/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(building))
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isBadRequest());
    }
}