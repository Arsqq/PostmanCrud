package com.example.casualcrudproject.service;

import com.example.casualcrudproject.domain.Employee;
import com.example.casualcrudproject.repo.EmployeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepo employeeRepo;
    private final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

    @Cacheable("employees")
    public List<Employee> findAll(){
        LOG.info("retrieving employees");
        return employeeRepo.findAll();
    }

    public Employee findById(Long id){
         if(employeeRepo.findById(id).isPresent()){
             return employeeRepo.findById(id).get();
         }
         else{
             LOG.info("----User with that id is not existing----");
             return null;
         }
    }

    public Employee save(Employee employee){
        return employeeRepo.save(employee);
    }

    public void delete(Employee employee){
      employeeRepo.delete(employee);
    }

    public List<Employee> findAllEmployeesById(Long id){
        return employeeRepo.findEmployeeByCategoryId(id);
    }


}
