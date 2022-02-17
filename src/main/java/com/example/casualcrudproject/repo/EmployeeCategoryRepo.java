package com.example.casualcrudproject.repo;

import com.example.casualcrudproject.domain.EmployeeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeCategoryRepo extends JpaRepository<EmployeeCategory,Long> {
    public EmployeeCategory findByCategoryName(String name);
}
