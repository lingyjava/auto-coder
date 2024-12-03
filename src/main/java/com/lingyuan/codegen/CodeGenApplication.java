package com.lingyuan.codegen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.lingyuan.codegen")
@EnableScheduling
public class CodeGenApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodeGenApplication.class, args);
    }
}