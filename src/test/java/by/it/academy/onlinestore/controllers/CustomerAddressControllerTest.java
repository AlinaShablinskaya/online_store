package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.dto.CustomerAddressDto;
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
class CustomerAddressControllerTest {
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
    @DisplayName("saveAddressTest should save new address to DB and return this address")
    @WithMockUser(username = "test@gmail.com", authorities = {"USER"})
    void saveAddressTest() throws Exception {
        CustomerAddressDto addressDto = new CustomerAddressDto();
        addressDto.setCountry("Country");
        addressDto.setStreet("Street");
        addressDto.setZipcode("112358");

        mockMvc.perform(post("/api/address/")
                .content(objectMapper.writeValueAsString(addressDto))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.zipcode", is("112358")))
                .andExpect(jsonPath("$.country", is("Country")))
                .andExpect(jsonPath("$.street", is("Street")));
    }

    @Test
    @WithMockUser(username = "test@gmail.com", authorities = {"USER"})
    @DisplayName("updateAddressTest should update address id in DB and return this address")
    void updateAddressTest() throws Exception {
        CustomerAddressDto addressDto = new CustomerAddressDto();
        addressDto.setId(1);
        addressDto.setCountry("England");
        addressDto.setStreet("NewStreet");
        addressDto.setZipcode("231103");

        mockMvc.perform(put("/api/address/")
                .content(objectMapper.writeValueAsString(addressDto))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.zipcode", is("231103")))
                .andExpect(jsonPath("$.country", is("England")))
                .andExpect(jsonPath("$.street", is("NewStreet")));
    }

    @Test
    @DisplayName("deleteAddressTest should delete address by id from DB")
    @WithMockUser(username = "test@gmail.com", authorities = {"USER"})
    void deleteAddressTest() throws Exception {
        mockMvc.perform(delete("/api/address/1"))
                .andExpect(status().isOk());
    }
}
