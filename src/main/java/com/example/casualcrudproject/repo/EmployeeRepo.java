package com.example.casualcrudproject.repo;

import com.example.casualcrudproject.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    Employee findEmployeeByName(String name);
    List<Employee> findEmployeeByCategoryId(Long id);

}
