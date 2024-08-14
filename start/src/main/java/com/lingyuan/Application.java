package com.lingyuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author LingYuan
 */
@SpringBootApplication
@ComponentScans(value = {
        @ComponentScan(value = "com.lingyuan.adapter"),
        @ComponentScan(value = "com.lingyuan.server"),
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
