package com.routy.routyback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication 
@MapperScan("com.routy.routyback.mapper")
public class RoutyBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoutyBackApplication.class, args);
    }

}
