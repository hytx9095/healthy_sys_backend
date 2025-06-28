package com.naiyin.healthy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.naiyin.healthy.mapper")
public class HealthySysApplication {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public AutowireCapableBeanFactory autowireCapableBeanFactory() {
        return applicationContext.getAutowireCapableBeanFactory();
    }

    public static void main(String[] args) {
        SpringApplication.run(HealthySysApplication.class, args);
    }
}
