package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.dto.product.ProductDto;
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
class ProductControllerTest {
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
    @DisplayName("saveProductTest should add new product to DB and return this product")
    @WithMockUser(username = "test@gmail.com", authorities = {"ADMIN"})
    void saveProductTest() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(4);
        productDto.setProductName("Product");
        productDto.setBrand("brand");
        productDto.setPhoto("path");
        productDto.setPrice(BigDecimal.valueOf(20));

        mockMvc.perform(post("/api/product/")
                .content(objectMapper.writeValueAsString(productDto))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.productName", is("Product")))
                .andExpect(jsonPath("$.brand", is("brand")))
                .andExpect(jsonPath("$.photo", is("path")))
                .andExpect(jsonPath("$.price", is(20)));
    }

    @Test
    @DisplayName("findProductByIdTest should retrieve product by id from DB")
    void findProductByIdTest() throws Exception {
        mockMvc.perform(get("/api/product/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.productName", is("Whiskey Jack Daniels")))
                .andExpect(jsonPath("$.brand", is("Jack Daniels")))
                .andExpect(jsonPath("$.photo", is("/images/goods/jack.jpg")))
                .andExpect(jsonPath("$.price", is(30)));
    }

    @Test
    @DisplayName("showAllProductTest should retrieve all product from DB")
    void showAllProductTest() throws Exception {
        mockMvc.perform(get("/api/product/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].productName", is("Whiskey Jack Daniels")))
                .andExpect(jsonPath("$[0].brand", is("Jack Daniels")))
                .andExpect(jsonPath("$[0].photo", is("/images/goods/jack.jpg")))
                .andExpect(jsonPath("$[0].price", is(30)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].productName", is("Beer Heineken Lager Beer")))
                .andExpect(jsonPath("$[1].brand", is("Heineken")))
                .andExpect(jsonPath("$[1].photo", is("/images/goods/heineken.jpg")))
                .andExpect(jsonPath("$[1].price", is(5)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].productName", is("Rum Captain Morgan")))
                .andExpect(jsonPath("$[2].brand", is("Captain Morgan")))
                .andExpect(jsonPath("$[2].photo", is("/images/goods/captain.jpg")))
                .andExpect(jsonPath("$[2].price", is(20)));
    }

    @Test
    @WithMockUser(username = "test@gmail.com", authorities = {"ADMIN"})
    @DisplayName("updateProductTest should update product by id in DB and return this product")
    void updateProductTest() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(1);
        productDto.setProductName("Jack Daniels");
        productDto.setBrand("Jack Daniels");
        productDto.setPhoto("/images/goods/jack.jpg");
        productDto.setPrice(BigDecimal.valueOf(40));

        mockMvc.perform(put("/api/product/")
                .content(objectMapper.writeValueAsString(productDto))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.productName", is("Jack Daniels")))
                .andExpect(jsonPath("$.brand", is("Jack Daniels")))
                .andExpect(jsonPath("$.photo", is("/images/goods/jack.jpg")))
                .andExpect(jsonPath("$.price", is(40)));
    }

    @Test
    @WithMockUser(username = "test@gmail.com", authorities = {"ADMIN"})
    @DisplayName("deleteProductTest should delete product by id from DB")
    void deleteProductTest() throws Exception {
        mockMvc.perform(delete("/api/product/1"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("showAllProductByCategoryTest should retrieve product by category name from DB")
    void showAllProductByCategoryTest() throws Exception {
        mockMvc.perform(get("/api/product/Whiskey/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].productName", is("Whiskey Jack Daniels")))
                .andExpect(jsonPath("$[0].brand", is("Jack Daniels")))
                .andExpect(jsonPath("$[0].photo", is("/images/goods/jack.jpg")))
                .andExpect(jsonPath("$[0].price", is(30)));
    }

    @Test
    @DisplayName("addProductToCatalogTest should create relationship between product and catalog")
    @WithMockUser(username = "test@gmail.com", authorities = {"ADMIN"})
    void addProductToCatalogTest() throws Exception {
        mockMvc.perform(post("/api/product/2/product?id=3"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("deleteProductFromCatalogTest should delete relationship between product and catalog from the table mapped")
    @WithMockUser(username = "test@gmail.com", authorities = {"ADMIN"})
    void deleteProductFromCatalogTest() throws Exception {
        mockMvc.perform(delete("/api/product/2/product?id=3"))
                .andExpect(status().isOk());
    }
}
