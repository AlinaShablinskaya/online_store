package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.dto.user.UserRequestDto;
import by.it.academy.onlinestore.entities.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Sql(value = "/table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("saveUserTest should add new user to DB and return this user")
    @WithMockUser(username = "test@gmail.com", authorities = {"USER"})
    void saveUserTest() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("FirstName");
        userRequestDto.setLastName("LastName");
        userRequestDto.setEmail("email@gmail.com");
        userRequestDto.setPassword("Aa123456");
        userRequestDto.setRole(String.valueOf(Role.USER));

        mockMvc.perform(post("/api/user/")
                .content(objectMapper.writeValueAsString(userRequestDto))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.firstName", is("FirstName")))
                .andExpect(jsonPath("$.lastName", is("LastName")))
                .andExpect(jsonPath("$.email", is("email@gmail.com")))
                .andExpect(jsonPath("$.password", is("Aa123456")))
                .andExpect(jsonPath("$.role", is("USER")));
    }

    @Test
    @DisplayName("saveUserTest should throw exception for invalid user email")
    @WithMockUser(username = "test@gmail.com", authorities = {"USER"})
    void saveInvalidUserEmailTest() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("FirstName");
        userRequestDto.setLastName("LastName");
        userRequestDto.setEmail("email.com");
        userRequestDto.setPassword("Aa123456");
        userRequestDto.setRole(String.valueOf(Role.USER));

        mockMvc.perform(post("/api/user/")
                        .content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("saveUserTest should throw exception for invalid user password")
    @WithMockUser(username = "test@gmail.com", authorities = {"USER"})
    void saveInvalidUserPasswordTest() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("FirstName");
        userRequestDto.setLastName("LastName");
        userRequestDto.setEmail("email@gmail.com");
        userRequestDto.setPassword("1234");
        userRequestDto.setRole(String.valueOf(Role.USER));

        mockMvc.perform(post("/api/user/")
                        .content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("findUserById should retrieve user by id from DB")
    @WithMockUser(username = "test@gmail.com", authorities = {"ADMIN"})
    void findUserById() throws Exception {
        mockMvc.perform(get("/api/user/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.firstName", is("Han")))
                .andExpect(jsonPath("$.lastName", is("Solo")))
                .andExpect(jsonPath("$.email", is("han@gmail.com")))
                .andExpect(jsonPath("$.role", is("USER")));
    }

    @Test
    @DisplayName("showAllUsers should retrieve all user from DB")
    @WithMockUser(username = "test@gmail.com", authorities = {"ADMIN"})
    void showAllUsers() throws Exception {
        mockMvc.perform(get("/api/user/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Lando")))
                .andExpect(jsonPath("$[0].lastName", is("Calrissian")))
                .andExpect(jsonPath("$[0].email", is("lando@gmail.com")))
                .andExpect(jsonPath("$[0].role", is("ADMIN")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Han")))
                .andExpect(jsonPath("$[1].lastName", is("Solo")))
                .andExpect(jsonPath("$[1].email", is("han@gmail.com")))
                .andExpect(jsonPath("$[1].role", is("USER")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].firstName", is("Hector")))
                .andExpect(jsonPath("$[2].lastName", is("Barbossa")))
                .andExpect(jsonPath("$[2].email", is("barbossa@gmail.com")))
                .andExpect(jsonPath("$[2].role", is("USER")));
    }
}
