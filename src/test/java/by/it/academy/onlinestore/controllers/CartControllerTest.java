package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.dto.CartDto;
import by.it.academy.onlinestore.dto.UserRequestDto;
import by.it.academy.onlinestore.entities.OrderItem;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Sql(value = "/table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CartControllerTest {
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
    @DisplayName("saveCartTest should save new cart to DB and return this cart")
    @WithMockUser(username = "test@gmail.com", authorities = {"USER"})
    void saveCartTest() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId(2);
        userRequestDto.setFirstName("Han");
        userRequestDto.setLastName("Solo");
        userRequestDto.setEmail("han@gmail.com");
        userRequestDto.setRole(String.valueOf(Role.USER));

        CartDto cartDto = new CartDto();
        cartDto.setId(4);
        cartDto.setUserRequestDto(userRequestDto);

        mockMvc.perform(post("/api/cart/")
                .content(objectMapper.writeValueAsString(cartDto))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.userRequestDto.id", is(2)))
                .andExpect(jsonPath("$.userRequestDto.firstName", is("Han")))
                .andExpect(jsonPath("$.userRequestDto.lastName", is("Solo")))
                .andExpect(jsonPath("$.userRequestDto.email", is("han@gmail.com")))
                .andExpect(jsonPath("$.userRequestDto.role", is("USER")));
    }

    @Test
    @DisplayName("findCartByIdTest should retrieve cart by id from DB")
    @WithMockUser(username = "test@gmail.com", authorities = {"USER"})
    void findCartByIdTest() throws Exception {
            mockMvc.perform(get("/api/cart/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.totalSum", is(90)))
                    .andExpect(jsonPath("$.userRequestDto.id", is(2)))
                    .andExpect(jsonPath("$.userRequestDto.firstName", is("Han")))
                    .andExpect(jsonPath("$.userRequestDto.lastName", is("Solo")))
                    .andExpect(jsonPath("$.userRequestDto.email", is("han@gmail.com")))
                    .andExpect(jsonPath("$.userRequestDto.role", is("USER")));
    }

    @Test
    @DisplayName("deleteCartByIdTest should delete cart by id from DB")
    @WithMockUser(username = "test@gmail.com", authorities = {"USER"})
    void deleteCartByIdTest() throws Exception  {
        mockMvc.perform(delete("/api/cart/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@gmail.com", authorities = {"USER"})
    @DisplayName("updateCartTest should update cart in DB and return this cart")
    void updateCartTest() throws Exception {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1);
        orderItem.setAmount(3);
        orderItem.setTotalPrice(BigDecimal.valueOf(90));

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        CartDto cartDto = new CartDto();
        cartDto.setId(1);
        cartDto.setOrderItemsDto(orderItems);

        mockMvc.perform(put("/api/cart/")
                .content(objectMapper.writeValueAsString(cartDto))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.totalSum", is(90)));
    }
}
