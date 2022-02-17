package com.example.casualcrudproject;

import com.example.casualcrudproject.domain.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CasualCrudProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CasualCrudProjectApplication.class, args);

    }

}
