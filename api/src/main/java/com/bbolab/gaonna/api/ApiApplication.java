package com.bbolab.gaonna.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "com.bbolab.gaonna")
@SpringBootApplication(scanBasePackages = "com.bbolab.gaonna")
public class ApiApplication {
    public static void main(String... args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
