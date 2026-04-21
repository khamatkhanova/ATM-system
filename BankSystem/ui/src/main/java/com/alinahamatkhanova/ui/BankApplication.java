package com.alinahamatkhanova.ui;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.alinahamatkhanova.ui", "com.alinahamatkhanova.bl", "com.alinahamatkhanova.infrastructure"})
@EnableJpaRepositories("com.alinahamatkhanova.infrastructure.repositories")
@EntityScan("com.alinahamatkhanova.infrastructure.models")
public class BankApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }
}