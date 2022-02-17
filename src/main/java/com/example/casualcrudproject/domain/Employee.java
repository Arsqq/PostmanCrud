package com.example.casualcrudproject.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private Long categoryId;

    public Employee(String name,Long id) {
        this.name=name;
        this.categoryId=id;
    }

    public Employee(String name) {
        this.name=name;
    }

    public Employee(){

    }
}

