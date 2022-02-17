package com.example.casualcrudproject.service;

import com.example.casualcrudproject.domain.Employee;
import com.example.casualcrudproject.domain.EmployeeCategory;
import com.example.casualcrudproject.repo.EmployeeCategoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeCategoryService {

    @Autowired
    EmployeeCategoryRepo employeeCategoryRepo;
    private final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

    public List<EmployeeCategory> findAll(){
        LOG.info("retrieving employeeCategories");
        return employeeCategoryRepo.findAll();
    }

    public EmployeeCategory findById(Long id){
        if(employeeCategoryRepo.findById(id).isPresent()){
            return employeeCategoryRepo.findById(id).get();
        }
        else{
            LOG.info("----Employee_Category with that id is not existing----");
            return null;
        }
    }

    public EmployeeCategory save(EmployeeCategory employeeCategory){
        return employeeCategoryRepo.save(employeeCategory);
    }

    public void delete(EmployeeCategory employeeCategory){
        employeeCategoryRepo.delete(employeeCategory);
    }

}
