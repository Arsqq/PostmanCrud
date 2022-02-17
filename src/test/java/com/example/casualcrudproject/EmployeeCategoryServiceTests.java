package com.example.casualcrudproject;


import com.example.casualcrudproject.domain.Employee;
import com.example.casualcrudproject.domain.EmployeeCategory;
import com.example.casualcrudproject.repo.EmployeeCategoryRepo;
import com.example.casualcrudproject.repo.EmployeeRepo;
import com.example.casualcrudproject.service.EmployeeCategoryService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmployeeCategoryServiceTests {

    private static final Long FIRST_EMPLOYEE_CATEGORY_ID=27L;
    private static final Long SECOND_EMPLOYEE_CATEGORY_ID=31L;
    private static final Long NUMBER_OF_CATEGORIES_ATM=3L;

    @MockBean
    EmployeeCategoryRepo employeeCategoryRepo;

    @Autowired
    EmployeeCategoryService employeeCategoryService;

    @Autowired
    ApplicationContext context;

    EmployeeCategory testEmployeeCategory;
    EmployeeCategory testEmployeeCategorySecond;
    Employee testEmployeeOne;
    Employee testEmployeeTwo;
    Employee testEmployeeThree;
    Employee testEmployeeFour;
    Employee testEmployeeFive;
    @Before
    public void init(){
        testEmployeeOne=new Employee("Ivan", FIRST_EMPLOYEE_CATEGORY_ID);
        testEmployeeTwo=new Employee("Ars", FIRST_EMPLOYEE_CATEGORY_ID);
        testEmployeeThree=new Employee("Yan", FIRST_EMPLOYEE_CATEGORY_ID);
        testEmployeeFour=new Employee("IvanEdited", FIRST_EMPLOYEE_CATEGORY_ID);
        testEmployeeFive=new Employee("AshenOne", SECOND_EMPLOYEE_CATEGORY_ID);


        testEmployeeCategory=new EmployeeCategory("Worker");
        testEmployeeCategory.getEmployees().add(testEmployeeOne);
        testEmployeeCategory.getEmployees().add(testEmployeeTwo);
        testEmployeeCategory.getEmployees().add(testEmployeeThree);
        testEmployeeCategory.getEmployees().add(testEmployeeFour);
        testEmployeeCategorySecond=new EmployeeCategory("Tester");
        testEmployeeCategorySecond.getEmployees().add(testEmployeeFive);
    }

    @Test
    public void findAllEmployeeCategoriesShouldReturnAllEmployeeCategories(){
        when(employeeCategoryRepo.findAll()).thenReturn(Stream.of(new EmployeeCategory("Tester"),
                new EmployeeCategory("Worker"),new EmployeeCategory("Manager")).
                collect(Collectors.toList()));
        Assertions.assertEquals(NUMBER_OF_CATEGORIES_ATM,employeeCategoryService.findAll().size());
    }

    @Test
    public void whenGetEmployeeById_ShouldReturnUser (){
        when(employeeCategoryRepo.findById(FIRST_EMPLOYEE_CATEGORY_ID)).thenReturn(java.util.Optional.ofNullable(testEmployeeCategory));
        Assertions.assertEquals(testEmployeeCategory,employeeCategoryService.findById(FIRST_EMPLOYEE_CATEGORY_ID));
        when(employeeCategoryRepo.findById(SECOND_EMPLOYEE_CATEGORY_ID)).thenReturn(java.util.Optional.ofNullable(testEmployeeCategorySecond));
        Assertions.assertEquals(testEmployeeCategorySecond,employeeCategoryService.findById(SECOND_EMPLOYEE_CATEGORY_ID));

    }

    @Test
    public void givenCountMethodMocked_WhenCountInvoked_ThenMockValueReturned() {
        Mockito.when(employeeCategoryRepo.count()).thenReturn(NUMBER_OF_CATEGORIES_ATM);
        EmployeeCategoryRepo employeeCategoryRepoFromContext = context.getBean(EmployeeCategoryRepo.class);
        long userCount = employeeCategoryRepoFromContext.count();
        Assertions.assertEquals(NUMBER_OF_CATEGORIES_ATM, userCount);
        verify(employeeCategoryRepo).count();
    }


    @Test
    public void whenSaveUser_shouldReturnUser() {
        when(employeeCategoryRepo.save(ArgumentMatchers.any(EmployeeCategory.class))).thenReturn(testEmployeeCategory);
        EmployeeCategory createdEmployeeCategory = employeeCategoryService.save(testEmployeeCategory);
        assertThat(createdEmployeeCategory.getCategoryName()).isSameAs(testEmployeeCategory.getCategoryName());
        Assertions.assertEquals(createdEmployeeCategory.getEmployees().size(),testEmployeeCategory.getEmployees().size());
        verify(employeeCategoryRepo).save(testEmployeeCategory);
    }

    @Test
    public void deleteEmployee(){
        employeeCategoryService.delete(testEmployeeCategory);
        verify(employeeCategoryRepo,times(1)).delete(testEmployeeCategory);
    }


}
