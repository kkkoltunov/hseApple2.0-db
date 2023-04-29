package com.hseapple.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@Configuration
@EnableJpaRepositories("com.hseapple")
@ComponentScan("com.hseapple")
@EntityScan("com.hseapple")
public class HseappleApplication {
    public static void main(String[] args) {
        SpringApplication.run(HseappleApplication.class, args);
    }

}
