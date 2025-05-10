package com.naiyin.healthy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.naiyin.healthy.mapper")
public class HealthySysApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthySysApplication.class, args);
    }

}
