package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.dto.OrderItemDto;
import by.it.academy.onlinestore.dto.ProductDto;
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

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Sql(value = "/table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class OrderItemControllerTest {
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
    @DisplayName("saveOrderItem should add new order Item to DB and return this order")
    @WithMockUser(username = "test@gmail.com", authorities = {"USER"})
    void saveOrderItem() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(1);
        productDto.setProductName("Whiskey Jack Daniels");
        productDto.setBrand("Jack Daniels");
        productDto.setPhoto("/images/goods/jack.jpg");
        productDto.setPrice(BigDecimal.valueOf(30));

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductDto(productDto);
        orderItemDto.setAmount(10);

        mockMvc.perform(post("/api/order/")
                .content(objectMapper.writeValueAsString(orderItemDto))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.amount", is(10)))
                .andExpect(jsonPath("$.totalPrice", is(300)))
                .andExpect(jsonPath("$.productDto.id", is(1)))
                .andExpect(jsonPath("$.productDto.productName", is("Whiskey Jack Daniels")))
                .andExpect(jsonPath("$.productDto.brand", is("Jack Daniels")))
                .andExpect(jsonPath("$.productDto.photo", is("/images/goods/jack.jpg")))
                .andExpect(jsonPath("$.productDto.price", is(30)));
    }

    @Test
    @DisplayName("saveOrderItemToCart should add order item to cart")
    @WithMockUser(username = "test@gmail.com", authorities = {"USER"})
    void saveOrderItemToCart() throws Exception {
        mockMvc.perform(post("/api/order/1/order?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@gmail.com", authorities = {"USER"})
    @DisplayName("deleteOrderItem should delete order by id from DB")
    void deleteOrderItem() throws Exception {
        mockMvc.perform(delete("/api/order/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("showAllOrderItemInCart should retrieve order item by cart id from DB")
    @WithMockUser(username = "test@gmail.com", authorities = {"USER"})
    void showAllOrderItemInCart() throws Exception {
        mockMvc.perform(get("/api/order/2/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].amount", is(10)))
                .andExpect(jsonPath("$[0].totalPrice", is(50)));
    }
}
