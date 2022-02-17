package com.example.casualcrudproject.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class EmployeeCategory {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name="categoryId")
    private List<Employee> employees=new ArrayList<>();

    public EmployeeCategory(String categoryName) {
        this.categoryName=categoryName;
    }

    public EmployeeCategory(){

    }


}
