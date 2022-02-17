package com.example.casualcrudproject.controllers;


import com.example.casualcrudproject.domain.Employee;
import com.example.casualcrudproject.domain.EmployeeCategory;
import com.example.casualcrudproject.repo.EmployeeCategoryRepo;
import com.example.casualcrudproject.repo.EmployeeRepo;
import com.example.casualcrudproject.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private EmployeeCategoryRepo employeeCategoryRepo;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public Employee findOneById(@PathVariable Long id){
        return employeeService.findById(id);
    }
    @GetMapping
    public List<Employee> findAll(){
       return employeeService.findAll();
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        if (!employeeCategoryRepo.existsById(employee.getCategoryId())) {
            employee.setCategoryId(null);
        }
        return employeeService.save(employee);

    }

    @RequestMapping(value ="/{id}", method=RequestMethod.DELETE)
    public void delete(@PathVariable("id") Employee employee) {
       employeeService.delete(employee);
    }

    @PutMapping({"/{id}"})
    public Employee replaceEmployee(@RequestBody Employee newEmployee,@PathVariable Long id){
        return employeeRepo.findById(id)
                .map(employee ->{
                    employee.setName(newEmployee.getName());
                    employee.setCategoryId(newEmployee.getCategoryId());
                    return employeeService.save(employee);
                }).orElseGet(()-> employeeService.save(newEmployee));
    }

    @GetMapping("/byId/{id}")
    public List<Employee> findAllByCategoryId(@PathVariable Long id){
        return employeeService.findAllEmployeesById(id);
    }
}
