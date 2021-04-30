package com.bbolab.gaonna.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "com.bbolab.gaonna")
@SpringBootApplication(scanBasePackages = "com.bbolab.gaonna")
public class CoreApplication {
    public static void main(String... args) {
        SpringApplication.run(CoreApplication.class, args);
    }
}
