package com.routy.routyback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.routy.routyback"})
@MapperScan(basePackages = {"com.routy.routyback"})
public class RoutyBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoutyBackApplication.class, args);
    }

}
