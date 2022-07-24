package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.dto.catalog.CatalogDto;
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
class CatalogControllerTest {
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
    @DisplayName("saveCatalogNameTest should add new catalog to DB and return this catalog")
    @WithMockUser(username = "test@gmail.com", authorities = {"ADMIN"})
    void saveCatalogNameTest() throws Exception {
        CatalogDto catalogDto = new CatalogDto();
        catalogDto.setGroupName("Name");

        mockMvc.perform(post("/api/catalog/")
                .content(objectMapper.writeValueAsString(catalogDto))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.groupName", is("Name")));
    }

    @Test
    @DisplayName("findCatalogNameByIdTest should retrieve catalog by id from DB")
    @WithMockUser(username = "test@gmail.com", authorities = {"ADMIN"})
    void findCatalogNameByIdTest() throws Exception {
        mockMvc.perform(get("/api/catalog/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.groupName", is("Whiskey")));
    }

    @Test
    @DisplayName("WithMockUser should retrieve all catalog from DB")
    @WithMockUser(username = "test@gmail.com", authorities = {"ADMIN"})
    void showAllCatalogNameTest() throws Exception {
        mockMvc.perform(get("/api/catalog/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].groupName", is("Whiskey")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].groupName", is("Rum")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].groupName", is("Beer")));
    }

    @Test
    @WithMockUser(username = "test@gmail.com", authorities = {"ADMIN"})
    @DisplayName("deleteCatalogTest should delete catalog by id from DB")
    void deleteCatalogTest() throws Exception {
        mockMvc.perform(delete("/api/catalog/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("updateCatalogTest should update catalog by id in DB and return this catalog")
    @WithMockUser(username = "test@gmail.com", authorities = {"ADMIN"})
    void updateCatalogTest() throws Exception {
        CatalogDto catalogDto = new CatalogDto();
        catalogDto.setId(1);
        catalogDto.setGroupName("NewName");

        mockMvc.perform(put("/api/catalog/")
                .content(objectMapper.writeValueAsString(catalogDto))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.groupName", is("NewName")));
    }
}
