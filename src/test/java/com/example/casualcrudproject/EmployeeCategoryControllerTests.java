package com.example.casualcrudproject;


import com.example.casualcrudproject.controllers.EmployeeCategoryController;
import com.example.casualcrudproject.controllers.EmployeeController;
import com.example.casualcrudproject.domain.EmployeeCategory;
import com.example.casualcrudproject.repo.EmployeeCategoryRepo;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeCategoryControllerTests {

    private static final String BASE_URL="http://localhost:";
    private static final Long ID_FOR_TESTING_CASE_ONE=27L;
    private static final Long ID_FOR_TESTING_CASE_TWO=31L;
    private static final String URL_FOR_GET_TESTING="/employeeCategory/27";

    @Autowired
    private EmployeeCategoryController categoryController;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    EmployeeCategoryRepo employeeCategoryRepo;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    List<EmployeeCategory> dataFromDb;

    HttpHeaders headers;
    HttpEntity<String> request;


    @BeforeEach
    public void init(){
        mockMvc= MockMvcBuilders.webAppContextSetup(wac).build();
        dataFromDb=employeeCategoryRepo.findAll();

        headers = new HttpHeaders();
        headers.setBasicAuth("John", "2");
         request = new HttpEntity<>(headers);
    }



    @Test
    public void contextLoads() throws Exception {
        assertThat(categoryController).isNotNull();
    }

    @Test
    public void findAllGetRequest_ShouldReturnRightCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + port + "/employeeCategory/")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].categoryName").
                        value(containsInAnyOrder(dataFromDb.get(0).
                                getCategoryName(),dataFromDb.get(1).
                                getCategoryName(),dataFromDb.get(2).getCategoryName())));
    }

    @Test
    public void findByIdGetRequest_ShouldReturnRightCategory() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + port + URL_FOR_GET_TESTING,
                HttpMethod.GET, request, String.class);

        String body = response.getBody();
        Assertions.assertTrue(body.contains(employeeCategoryRepo.findById(ID_FOR_TESTING_CASE_ONE).get().getCategoryName()));
    }

}
