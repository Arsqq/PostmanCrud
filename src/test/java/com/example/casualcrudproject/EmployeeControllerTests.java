package com.example.casualcrudproject;

import com.example.casualcrudproject.WebConfig.SecurityConfig;
import com.example.casualcrudproject.controllers.EmployeeController;
import com.example.casualcrudproject.domain.Employee;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTests {


    private static final Long FIRST_EMPLOYEE_CATEGORY_ID=27L;
    private static final Long SECOND_EMPLOYEE_CATEGORY_ID=43L;
    private static final String BASE_URL="http://localhost:";
    private static final String GET_BY_ID_TEST_URL_ONE="/employee/44";
    private static final String GET_BY_ID_TEST_URL_TWO="/employee/43";

    @Autowired
    private EmployeeController employeeController;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    Map<Employee,Long> employeeMap;
    Employee testEmployeeOne;
    Employee testEmployeeTwo;
    Employee testEmployeeThree;
    Employee testEmployeeFour;
    Employee testEmployeeFive;
    HttpHeaders headers;
    HttpEntity<String> request;

    @BeforeEach
    public void init(){
        employeeMap=new HashMap<>();
        testEmployeeOne=new Employee("Ivan", FIRST_EMPLOYEE_CATEGORY_ID);
        testEmployeeTwo=new Employee("Ars",FIRST_EMPLOYEE_CATEGORY_ID);
        testEmployeeThree=new Employee("Yan", FIRST_EMPLOYEE_CATEGORY_ID);
        testEmployeeFour=new Employee("IvanEdited", FIRST_EMPLOYEE_CATEGORY_ID);
        testEmployeeFive=new Employee("AshenOne", SECOND_EMPLOYEE_CATEGORY_ID);


        employeeMap.put(testEmployeeOne,testEmployeeOne.getCategoryId());
        employeeMap.put(testEmployeeFive,testEmployeeFive.getCategoryId());

        mockMvc= MockMvcBuilders.webAppContextSetup(wac).build();



        headers = new HttpHeaders();
        headers.setBasicAuth("John", "2");
        HttpEntity<String> request = new HttpEntity<>(headers);
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(employeeController).isNotNull();
    }

  @Test
    public void getMyIdShouldReturnRightEmployeeNumOne() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + port + GET_BY_ID_TEST_URL_ONE,
                HttpMethod.GET, request, String.class);

        String body = response.getBody();
        Assertions.assertTrue(body.contains(employeeMap.get(testEmployeeOne).toString()));
    }

    @Test
    public void getMyIdShouldReturnRightEmployeeNumTwo() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + port + GET_BY_ID_TEST_URL_TWO,
                HttpMethod.GET, request, String.class);

        String body = response.getBody();
        Assertions.assertTrue(body.contains(employeeMap.get(testEmployeeFive).toString()));
    }

    @Test
    public void rootRequestShouldReturnAllEmployees() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + port + "/employee")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name").
                        value(containsInAnyOrder(testEmployeeOne.getName(),testEmployeeTwo.getName(),
                                testEmployeeThree.getName(),testEmployeeFour.getName(),testEmployeeFive.getName())));
    }
/*
   @Test
    public void testFooDelete() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(BASE_URL+port+"/employee/{id}",28)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
*/

}
