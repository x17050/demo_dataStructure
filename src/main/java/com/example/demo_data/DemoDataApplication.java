package com.example.demo_data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.demo_data.mapper")
public class DemoDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoDataApplication.class, args);
    }

}
