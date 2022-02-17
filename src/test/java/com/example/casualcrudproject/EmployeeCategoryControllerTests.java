package com.example.casualcrudproject;


import com.example.casualcrudproject.controllers.EmployeeCategoryController;
import com.example.casualcrudproject.controllers.EmployeeController;
import com.example.casualcrudproject.domain.EmployeeCategory;
import com.example.casualcrudproject.repo.EmployeeCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeCategoryControllerTests {

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






}
