package com.lingyuan.autocoder.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author LingYuan
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.lingyuan.autocoder")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
