package com.sales.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.sales.api.model"})
@EnableJpaRepositories({"com.sales.api.service.repositories"})
@SpringBootApplication
public class SalesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalesApiApplication.class, args);



    }

}
