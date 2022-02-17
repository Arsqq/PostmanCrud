package com.example.casualcrudproject;

import com.example.casualcrudproject.domain.Employee;
import com.example.casualcrudproject.repo.EmployeeRepo;
import com.example.casualcrudproject.service.EmployeeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class EmployeeServiceTests {

    private static final Long FIRST_EMPLOYEE_CATEGORY_ID=27L;
    private static final Long SECOND_EMPLOYEE_CATEGORY_ID=31L;
    private static final Long NUMBER_OF_ALL_EMPLOYEES_ATM=5L;



    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepo employeeRepo;

    @Autowired
    ApplicationContext context;

    Employee testEmployeeOne;
    Employee testEmployeeTwo;
    Employee testEmployeeThree;
    Employee testEmployeeFour;
    Employee testEmployeeFive;
    List<Employee> firstCategoryList;

    @BeforeEach
    public void init(){
        testEmployeeOne=new Employee("Ivan", FIRST_EMPLOYEE_CATEGORY_ID);
        testEmployeeTwo=new Employee("Ars", FIRST_EMPLOYEE_CATEGORY_ID);
        testEmployeeThree=new Employee("Yan", FIRST_EMPLOYEE_CATEGORY_ID);
        testEmployeeFour=new Employee("IvanEdited", FIRST_EMPLOYEE_CATEGORY_ID);
        testEmployeeFive=new Employee("AshenOne", SECOND_EMPLOYEE_CATEGORY_ID);

        firstCategoryList= Arrays.asList(testEmployeeOne,testEmployeeTwo,testEmployeeThree,testEmployeeFour);

    }



   @Test
   public void whenFindAllEmployees_ShouldReturnAllEmployees(){
       when(employeeRepo.findAll()).
               thenReturn(Stream.of(testEmployeeOne,testEmployeeTwo,testEmployeeThree,testEmployeeFour,testEmployeeFive)
                       .collect(Collectors.toList()));
               Assertions.assertEquals(NUMBER_OF_ALL_EMPLOYEES_ATM,employeeService.findAll().size());
   }
    @Test
    public void whenGetEmployeeById_ShouldReturnEmployee (){
       when(employeeRepo.findById(FIRST_EMPLOYEE_CATEGORY_ID)).thenReturn(java.util.Optional.ofNullable(testEmployeeOne));
       Assertions.assertEquals(testEmployeeOne,employeeService.findById(FIRST_EMPLOYEE_CATEGORY_ID));
    }
    @Test
    public void givenCountMethodMocked_WhenCountInvoked_ThenMockValueReturned() {
        Mockito.when(employeeRepo.count()).thenReturn(NUMBER_OF_ALL_EMPLOYEES_ATM);
        EmployeeRepo employeeRepoFromContext = context.getBean(EmployeeRepo.class);
        long userCount = employeeRepoFromContext.count();
        Assertions.assertEquals(NUMBER_OF_ALL_EMPLOYEES_ATM, userCount);
        verify(employeeRepo).count();
    }

    @Test
    public void whenSaveUser_shouldReturnUser() {
        when(employeeRepo.save(ArgumentMatchers.any(Employee.class))).thenReturn(testEmployeeOne);
        Employee createdEmployee = employeeService.save(testEmployeeOne);
        assertThat(createdEmployee.getName()).isSameAs(testEmployeeOne.getName());
        verify(employeeRepo).save(testEmployeeOne);
    }

    @Test
    public void deleteEmployee(){
        employeeService.delete(testEmployeeOne);
        verify(employeeRepo,times(1)).delete(testEmployeeOne);
    }
    @Test
    public void whenGetAllEmployeesById_ShouldReturnAllEmployeesWithId (){
        when(employeeRepo.findEmployeeByCategoryId(FIRST_EMPLOYEE_CATEGORY_ID)).
                thenReturn(Stream.of(testEmployeeOne,testEmployeeTwo,testEmployeeThree,testEmployeeFour).collect(Collectors.toList()));
        Assertions.assertEquals(firstCategoryList.size(),employeeService.findAllEmployeesById(FIRST_EMPLOYEE_CATEGORY_ID).size());
    }




}
