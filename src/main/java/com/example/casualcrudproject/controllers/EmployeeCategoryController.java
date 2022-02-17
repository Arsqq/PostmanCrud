package com.example.casualcrudproject.controllers;

import com.example.casualcrudproject.domain.Employee;
import com.example.casualcrudproject.domain.EmployeeCategory;
import com.example.casualcrudproject.repo.EmployeeCategoryRepo;
import com.example.casualcrudproject.repo.EmployeeRepo;
import com.example.casualcrudproject.service.EmployeeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employeeCategory")
public class EmployeeCategoryController {
    @Autowired
    private EmployeeCategoryRepo employeeCategoryRepo;

    @Autowired
    EmployeeCategoryService employeeCategoryService;

    @GetMapping
    public List<EmployeeCategory> findAll(){
        return employeeCategoryService.findAll();
    }

    @PostMapping
    public EmployeeCategory addEmployeeCategory(@RequestBody EmployeeCategory employeeCategory){
        return employeeCategoryService.save(employeeCategory);
    }

    @RequestMapping(value ="/{name}", method=RequestMethod.DELETE)
    public void delete(@PathVariable("name") EmployeeCategory employeeCategory) {
        employeeCategoryService.delete(employeeCategory);
    }

    @PutMapping({"/{id}"})
    public EmployeeCategory replaceEmployeeCategory(@RequestBody EmployeeCategory newEmployeeCategory,
                                                    @PathVariable Long id){
        return employeeCategoryRepo.findById(id)
                .map(employeeCategory ->{
                    employeeCategory.setCategoryName(newEmployeeCategory.getCategoryName());
                    return employeeCategoryService.save(employeeCategory);
                }).orElseGet(()-> employeeCategoryService.save(newEmployeeCategory));
    }

    @GetMapping("/{id}")
    public EmployeeCategory findOneById(@PathVariable Long id){
        return employeeCategoryService.findById(id);
    }
}


